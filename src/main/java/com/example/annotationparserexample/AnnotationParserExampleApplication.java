package com.example.annotationparserexample;

import com.example.annotationparserexample.bpp.AutowireImplBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(AutowireImplBeanPostProcessor.class)
@SpringBootApplication
public class AnnotationParserExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationParserExampleApplication.class, args);
    }

}
