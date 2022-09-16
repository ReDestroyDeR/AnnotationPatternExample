package com.example.annotationparserexample.service;

import com.example.annotationparserexample.annotation.ActionResolver;
import com.example.annotationparserexample.dto.ActionDto;
import com.example.annotationparserexample.enums.ActionType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public abstract class AbstractClass {
    private final Map<ActionType, Method> methodMap;

    protected AbstractClass() {
        methodMap = Arrays.stream(this.getClass().getMethods())
                .filter(method -> nonNull(method.getAnnotation(ActionResolver.class)))
                .filter(method -> method.getReturnType().equals(String.class))
                .collect(Collectors.toMap(
                        method -> method.getAnnotation(ActionResolver.class).value(),
                        Function.identity()
                ));
    }

    protected AbstractClass getSelfReference() {
        return this;
    }

    public String processAction(ActionDto dto) {
        Method actionResolver = methodMap.get(dto.getActionType());
        try {
            return (String) actionResolver.invoke(getSelfReference(), dto);
        } catch (InvocationTargetException | IllegalAccessException e) {
            if (e.getCause() instanceof RuntimeException)
                throw (RuntimeException) e.getCause();

            throw new RuntimeException(e);
        }
    }
}
