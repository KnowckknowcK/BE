package com.knu.KnowcKKnowcK.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ExampleDto {
    private String returnMessage;
    private LocalDate localDate;
}
