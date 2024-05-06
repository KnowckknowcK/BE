package com.knu.KnowcKKnowcK.controller.myInfo;

import com.knu.KnowcKKnowcK.service.myPage.MySummaryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class MySummaryControllerTest {

    @InjectMocks
    private MySummaryService mySummaryService;

    @Test
    @DisplayName("사용자가 작성중인 요약문 확인")
    void getMySummary() {

    }
}