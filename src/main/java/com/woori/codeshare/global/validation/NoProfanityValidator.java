package com.woori.codeshare.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class NoProfanityValidator implements ConstraintValidator<NoProfanity, String> {

    private static final List<String> PROFANITIES = Arrays.asList("욕설1", "욕설2", "욕설3");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        for (String profanity : PROFANITIES) {
            if (value.contains(profanity)) {
                return false;
            }
        }

        return true;
    }
}
