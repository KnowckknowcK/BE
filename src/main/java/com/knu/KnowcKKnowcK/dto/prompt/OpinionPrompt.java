package com.knu.KnowcKKnowcK.dto.prompt;

public class OpinionPrompt {
    private String prompt;
    private OpinionPrompt(){
        prompt = "아래 기사 내용에 대한 내 의견을 작성한 것에 대한 평가를 해줘 \n\n";
    }
    private static class HOLDER{
        public static final OpinionPrompt INSTANCE = new OpinionPrompt();
    }
    public static OpinionPrompt getInstance(){
        return OpinionPrompt.HOLDER.INSTANCE;
    }
    public String getPrompt(){
        return prompt;
    }
}
