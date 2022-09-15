package com.example.annotationparserexample.validator;

import com.example.annotationparserexample.annotation.NameNotNull;
import com.example.annotationparserexample.dto.ActionDto;
import com.example.annotationparserexample.enums.ActionType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NameNotNullValidator implements ConstraintValidator<NameNotNull, ActionDto> {
    @Override
    public boolean isValid(ActionDto value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value.getName());
    }
}
