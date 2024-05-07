package com.knu.KnowcKKnowcK.dto.prompt;

public class SummaryPrompt {
    private String prompt;
    private SummaryPrompt(){
        prompt = "내가 기사 내용과 요약한 내용을 전달하면 이것에 대한 평가를 \n" +
                "\"GOOD#요약을 잘했습니다.\" 형식으로 내려줘\n";
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
