package com.knu.KnowcKKnowcK.dto.prompt;

public class OpinionPrompt {
    private String prompt;
    private OpinionPrompt(){
        prompt = "위 기사내용에 대한 의견을 작성한 것에 대한 평가를 해줘 \n" +
                "요약내용은 기사내용에 대한 나의 의견을 서술한 것이다." +
                "요약내용에 대해 핵심 내용 포함 여부, 사용한 어휘나 문장 구조, 내용에 대해 개선 방안을 적어줘.";
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
