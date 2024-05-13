package com.knu.KnowcKKnowcK.dto.prompt;

public class SummaryPrompt {
    private String prompt;
    private SummaryPrompt(){
        prompt = "위 요약에 대해 피드백 해줘.\n" +
                "1. 평가 점수는 다음 3가지 중 하나로 정해줘. (GOOD, NORMAL, FAIR)\n" +
                "2. 평가 점수는 대학교 교수님과 같이 아주 엄격하게 따져서 매겨줘.\n" +
                "3. 평가 점수 뒤에 #을 붙이고 요약에 대해 핵심 내용 포함 여부, 사용한 어휘나 문장 구조, 내용에 대해 개선 방안을 적어줘.\n" +
                "\n" +
                "따라서 완성된 피드백의 형태는 다음과 같아야 해. 평가점수#피드백 내용";
    }
    private static class HOLDER{
        public static final SummaryPrompt INSTANCE = new SummaryPrompt();
    }
    public static SummaryPrompt getInstance(){
        return HOLDER.INSTANCE;
    }
    public String getPrompt(){
        return prompt;
    }
}
