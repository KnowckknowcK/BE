package com.knu.KnowcKKnowcK.converter;


import com.knu.KnowcKKnowcK.enums.Score;
import org.springframework.core.convert.converter.Converter;

public class StringToScoreConverter implements Converter<String, Score> {

    @Override
    public Score convert(String source) {
        return Score.valueOf(source);
    }
}
