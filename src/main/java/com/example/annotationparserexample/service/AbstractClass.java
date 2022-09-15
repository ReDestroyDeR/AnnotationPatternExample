package com.example.annotationparserexample.service;

import com.example.annotationparserexample.annotation.ActionResolver;
import com.example.annotationparserexample.annotation.NameNotNull;
import com.example.annotationparserexample.dto.ActionDto;
import com.example.annotationparserexample.enums.ActionType;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public abstract class AbstractClass <Self extends AbstractClass<?>> {
    protected Self self;

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

    public String processAction(ActionDto dto) {
        Method raw = methodMap.get(dto.getActionType());
        try {
            Method proxied = self.getClass().getMethod(raw.getName(), raw.getParameterTypes());
            return (String) proxied.invoke(self, dto);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            if (e.getCause() instanceof RuntimeException)
                throw (RuntimeException) e.getCause();

            throw new RuntimeException(e);
        }
    }
}
