package com.example.annotationparserexample.dto;

import com.example.annotationparserexample.enums.ActionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionDto {
    private String name;
    private ActionType actionType;
}
