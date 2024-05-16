package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.requestdto.ProfileUpdateRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.DashboardResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.ProfileResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.*;
import com.knu.KnowcKKnowcK.utils.AwsS3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final AwsS3Util awsS3Util;
    //멤버의 프로필 정보 응답
    @Transactional
    public ProfileResponseDto getProfile(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->  new CustomException(ErrorCode.INVALID_INPUT));
        return new ProfileResponseDto(member);
    }

    //멤버의 프로필 정보 업데이트
    @Transactional
    public void updateProfile(String email, ProfileUpdateRequestDto request, MultipartFile profileImg){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        //이미지 파일 String 타입으로 변환
        if (!profileImg.isEmpty()) {
            String imgName = awsS3Util.uploadFile(profileImg);
            member.updateProfile(request.getName(), request.getPassword(), imgName);
        }
        member.updateProfile(request.getName(), request.getPassword());
    }

    //대시보드 정보 조회
    @Transactional
    public DashboardResponseDto getDashboardInfo(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        List<Summary> totalSummaries = member.getSummaries();
        List<Opinion> totalOpinions = member.getOpinions();
        int totalSummaryCount = 0;
        int totalOpinionCount = 0;
        //오늘 날짜
        LocalDate today = LocalDate.now();
        long todaySummariesCount = 0L;
        long todayOpinionsCount = 0L;
        //연속 일자
        int consecutiveDays = 0;

        if (!totalSummaries.isEmpty()){
            //연속 참여 횟수 계산을 위한 createdTime 기준, 내림차순으로 정렬
            totalSummaries = totalSummaries.stream()
                    .sorted(Comparator.comparing(Summary::getCreatedTime).reversed())
                    .toList();
            LocalDate previousDate = null;
            //연속 횟수 계산
            for (Summary summary : totalSummaries) {
                LocalDate summaryDate = summary.getCreatedTime().toLocalDate();
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

            //오늘 작성한 요약개수
            todaySummariesCount = totalSummaries.stream().filter(s -> s.getCreatedTime().toLocalDate().equals(today)).count();

            //총 요약개수
            totalSummaryCount = totalSummaries.size();
        }
        if (!totalOpinions.isEmpty()){
            //오늘 작성한 견해개수
            todayOpinionsCount = totalOpinions.stream().filter(o -> o.getCreatedTime().toLocalDate().equals(today)).count();
            //총 견해개수
            totalOpinionCount = totalOpinions.size();
        }

        //오늘 작성한 요약과 견해 총 개수
        Long totalTodayWorks = todaySummariesCount + todayOpinionsCount;

        return new DashboardResponseDto(totalTodayWorks,totalSummaryCount,totalOpinionCount,consecutiveDays);
    }
}
