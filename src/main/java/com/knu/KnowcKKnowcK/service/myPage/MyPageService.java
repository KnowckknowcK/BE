package com.knu.KnowcKKnowcK.service.myPage;

import com.knu.KnowcKKnowcK.domain.*;
import com.knu.KnowcKKnowcK.dto.requestdto.ProfileUpdateRequestDto;
import com.knu.KnowcKKnowcK.dto.responsedto.DashboardResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.DebateRoomResponseDto;
import com.knu.KnowcKKnowcK.dto.responsedto.ProfileResponseDto;
import com.knu.KnowcKKnowcK.exception.CustomException;
import com.knu.KnowcKKnowcK.exception.ErrorCode;
import com.knu.KnowcKKnowcK.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final SummaryRepository summaryRepository;
    private final OpinionRepository opinionRepository;
    //멤버의 프로필 정보 응답
    @Transactional
    public ProfileResponseDto getProfile(Long id){
        Member member = memberRepository.findById(id).orElseThrow(() ->  new CustomException(ErrorCode.INVALID_INPUT));
        return new ProfileResponseDto(member);
    }

    //멤버의 프로필 정보 업데이트
    @Transactional
    public void updateProfile(Long id, ProfileUpdateRequestDto request){
        Member member = memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        member.updateProfile(request.getName(), request.getEmail(), request.getProfileImage());
    }

    //대시보드 정보 조회
    @Transactional
    public DashboardResponseDto getDashboardInfo(Long id){
        Member member = memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        List<Summary> totalSummaries = member.getSummaries();
        List<Opinion> totalOpinions = member.getOpinions();

        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        //연속 참여 횟수 계산을 위한 createdTime 기준, 내림차순으로 정렬
        totalSummaries = totalSummaries.stream()
                .sorted(Comparator.comparing(Summary::getCreatedTime).reversed())
                .toList();
        //오늘 날짜
        LocalDate today = LocalDate.now();
        int consecutiveDays = 0;

        //연속 횟수 계산
        for (Summary summary : totalSummaries) {
            LocalDate summaryDate = summary.getCreatedTime().toLocalDate();
            // 오늘 날짜부터 시작하여 연속된 일수 계산
            if (summaryDate.equals(today.minusDays(consecutiveDays))) {
                consecutiveDays++;
            } else {
                // 연속이 끊기면 반복 종료
                break;
            }
        }

        Long todaySummariesCount = totalSummaries.stream().filter(s -> s.getCreatedTime().toLocalDate().equals(today)).count();
        Long todayOpinionsCount = totalOpinions.stream().filter(o -> o.getCreatedTime().toLocalDate().equals(today)).count();
        //오늘 작성한 요약과 견해 개수
        Long totalTodayWorks = todaySummariesCount + todayOpinionsCount;

        return new DashboardResponseDto(totalTodayWorks,totalSummaries.size(),totalOpinions.size(),consecutiveDays);
    }
}
