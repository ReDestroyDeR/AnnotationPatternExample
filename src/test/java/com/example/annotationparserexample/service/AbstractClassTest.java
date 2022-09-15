package com.example.annotationparserexample.service;

import com.example.annotationparserexample.dto.ActionDto;
import com.example.annotationparserexample.enums.ActionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AbstractClassTest {
    @Autowired
    private ImplementationAlpha alpha;

    @Autowired
    private ImplementationBeta beta;

    @Test
    void testAction1() {
        var action1WithName = ActionDto.builder()
                .name("Name")
                .actionType(ActionType.ACTION_1)
                .build();

        var action1WithoutName = ActionDto.builder()
                .actionType(ActionType.ACTION_1)
                .build();

        assertEquals("Alpha 1", alpha.processAction(action1WithName));
        assertThrows(ConstraintViolationException.class, () -> alpha.processAction(action1WithoutName));

        assertEquals("Beta 1", beta.processAction(action1WithName));
        assertEquals("Beta 1", beta.processAction(action1WithoutName));
    }

    @Test
    void testAction2() {
        var action2WithName = ActionDto.builder()
                .name("Name")
                .actionType(ActionType.ACTION_2)
                .build();

        var action2WithoutName = ActionDto.builder()
                .actionType(ActionType.ACTION_2)
                .build();

        assertEquals("Alpha 2", alpha.processAction(action2WithName));
        assertEquals("Alpha 2", alpha.processAction(action2WithoutName));

        assertEquals("Beta 2", beta.processAction(action2WithName));
        assertThrows(ConstraintViolationException.class, () -> beta.processAction(action2WithoutName));
    }

    @Test
    void testAction3() {
        var action3WithName = ActionDto.builder()
                .name("Name")
                .actionType(ActionType.ACTION_3)
                .build();

        var action3WithoutName = ActionDto.builder()
                .actionType(ActionType.ACTION_3)
                .build();

        assertEquals("Alpha 3", alpha.processAction(action3WithName));
        assertEquals("Alpha 3", alpha.processAction(action3WithoutName));

        assertEquals("Beta 3", beta.processAction(action3WithName));
        assertEquals("Beta 3", beta.processAction(action3WithoutName));
    }

}