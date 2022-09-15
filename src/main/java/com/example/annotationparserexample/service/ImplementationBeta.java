package com.example.annotationparserexample.service;

import com.example.annotationparserexample.annotation.ActionResolver;
import com.example.annotationparserexample.annotation.NameNotNull;
import com.example.annotationparserexample.dto.ActionDto;
import com.example.annotationparserexample.enums.ActionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;

@Slf4j
@Validated
@Component
public class ImplementationBeta extends AbstractClass<ImplementationBeta> {
    @Lazy
    @Autowired
    private ImplementationBeta privateSelf;

    @PostConstruct
    public void selfInjectInAbstractClass() {
        this.self = privateSelf;
    }

    @ActionResolver(ActionType.ACTION_1)
    public String processA(ActionDto actionDto) {
        log.info("A Beta {}", actionDto);
        return "Beta 1";
    }

    @ActionResolver(ActionType.ACTION_2)
    public String processB(@NameNotNull ActionDto actionDto) {
        log.info("B Beta {}", actionDto);
        return "Beta 2";
    }

    @ActionResolver(ActionType.ACTION_3)
    public String processC(ActionDto actionDto) {
        log.info("C Beta {}", actionDto);
        return "Beta 3";
    }
}
