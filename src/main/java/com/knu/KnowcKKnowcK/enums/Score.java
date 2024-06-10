package com.knu.KnowcKKnowcK.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Score {
    //점수를 상/중/하 로 나타낸다
    EXCELLENT("EXCELLENT", 50),
    GOOD("GOOD", 25),
    FAIR("FAIR", 10);

    private final String scoreString;
    private final Integer exp;
}
