package com.knu.KnowcKKnowcK.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class MailUtil {
    private final TemplateEngine templateEngine;
    public String mailTemplate(String code){
        Context context = new Context();
        context.setVariable("code",code);
        return templateEngine.process("mail",context);
    }
}
