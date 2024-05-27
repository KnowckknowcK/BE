package com.knu.KnowcKKnowcK.service.myPage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.requestdto.ProfileUpdateRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.DashboardResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.ProfileResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.*;
import com.knu.KnowcKKnowcK.utils.AwsS3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final SummaryFeedbackRepository summaryFeedbackRepository;
    private final AwsS3Util awsS3Util;
    private final BCryptPasswordEncoder passwordEncoder;
    //멤버의 프로필 정보 응답
    @Transactional
    public ProfileResponseDto getProfile(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->  new CustomException(ErrorCode.INVALID_INPUT));
        return new ProfileResponseDto(member);
    }

    //멤버의 프로필 정보 업데이트
    @Transactional
    public void updateProfile(String email, String request, MultipartFile profileImg) throws JsonProcessingException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        //이미지 파일 String 타입으로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        ProfileUpdateRequestDto requestDto = objectMapper.readValue(request, ProfileUpdateRequestDto.class);
        String name = member.getName();
        String password = member.getPassword();
        String imgName = member.getProfileImage();
        if (requestDto.getPassword() != null){
            password = passwordEncoder.encode(requestDto.getPassword());
        }

        if (requestDto.getName() != null){
            name = requestDto.getName();
        }

        if (profileImg != null) {
            imgName = awsS3Util.uploadFile(profileImg);
            member.updateProfile(name, password, imgName);
        }
        member.updateProfile(name, password,imgName);
    }

    //대시보드 정보 조회
    @Transactional
    public DashboardResponseDto getDashboardInfo(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        List<SummaryFeedback> totalDoneSummaries = summaryFeedbackRepository.findSummaryFeedbacksWithSummaries(member).orElse(new ArrayList<>());
        List<Opinion> totalOpinions = member.getOpinions();
        int totalSummaryCount = 0;
        int totalOpinionCount = 0;
        //오늘 날짜
        LocalDate today = LocalDate.now();
        long todayOpinionsCount = 0L;
        //
        SimpleEntry<Long,Integer> todaySummariesAndScore = new SimpleEntry<>(0L,0);
        //연속 일자
        int consecutiveDays = 0;


        if (!totalDoneSummaries.isEmpty()){
            //연속 참여 횟수 계산을 위한 createdTime 기준, 내림차순으로 정렬
            totalDoneSummaries = totalDoneSummaries.stream()
                    .sorted(Comparator.comparing((SummaryFeedback sf) -> sf.getSummary().getCreatedTime()).reversed())
                    .toList();
            LocalDate previousDate = null;
            //연속 횟수 계산
            for (SummaryFeedback sf : totalDoneSummaries) {
                LocalDate summaryDate = sf.getSummary().getCreatedTime().toLocalDate();
                // 오늘 날짜부터 시작하여 연속된 일수 계산
                if (!summaryDate.equals(previousDate)){
                    if (summaryDate.equals(today.minusDays(consecutiveDays))) {
                        previousDate = summaryDate;
                        consecutiveDays++;
                    } else {
                        // 연속이 끊기면 반복 종료
                        break;
                    }
                }
            }

            //오늘 작성한 요약개수와 오늘 획득한 경험치 계산
            todaySummariesAndScore= totalDoneSummaries.stream()
                    .filter(sf -> sf.getSummary().getCreatedTime().toLocalDate().equals(today))
                    .collect(Collectors.teeing(
                            Collectors.counting(),
                            Collectors.summingInt(sf -> sf.getScore().getExp()),
                            SimpleEntry::new
                    ));

            //총 요약개수
            totalSummaryCount = member.getSummaries().size();
        }
        if (!totalOpinions.isEmpty()){
            //오늘 작성한 견해개수
            todayOpinionsCount = totalOpinions.stream().filter(o -> o.getCreatedTime().toLocalDate().equals(today)).count();
            //총 견해개수
            totalOpinionCount = totalOpinions.size();
        }

        //오늘 작성한 요약과 견해 총 개수
        Long totalTodayWorks = todaySummariesAndScore.getKey() + todayOpinionsCount;

        return new DashboardResponseDto(totalTodayWorks,totalSummaryCount,totalOpinionCount,consecutiveDays,member.getPoint(),todaySummariesAndScore.getValue());
    }
}
