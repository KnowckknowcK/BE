package com.knu.KnowcKKnowcK.dto.prompt;

public class SummaryPrompt {
    private String prompt;
    private SummaryPrompt(){
        prompt = "신문기사 내용과 이에 대한 요약에 대해 문해력 피드백과 100점 기준의 점수를 주는 것이 당신의 역할입니다. 신문 기사 내용와 요약은 줄바꿈으로 구분합니다. 피드백의 양식은 \"score(only integer)#feedback(String)\" 입니다. 아래에 신문 기사 내용과 요약을 작성할테니 이에 대한 피드백을 양식을 꼭 지켜서 만들어주세요. 피드백만 작성하세요. \n\n";
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
