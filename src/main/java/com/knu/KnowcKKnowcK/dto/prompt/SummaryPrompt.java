package com.knu.KnowcKKnowcK.dto.prompt;

public class SummaryPrompt {
    private String prompt;
    private SummaryPrompt(){
        prompt = "그냥 제 말을 따라하세요. \"GOOD#내용이 아주 좋은 것 같아요.\" \n\n";
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
