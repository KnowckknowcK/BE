package com.knu.KnowcKKnowcK.dto.prompt;

public class SummaryPrompt {
    private String prompt;
    private SummaryPrompt(){
        prompt = "아래 기사 내용을 요약한 것에 대한 평가를 해줘 \n\n";
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
