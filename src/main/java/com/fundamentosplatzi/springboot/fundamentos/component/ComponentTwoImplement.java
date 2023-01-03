package com.fundamentosplatzi.springboot.fundamentos.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ComponentTwoImplement implements ComponentDependency {
    @Override
    public void saludar() {
        System.out.println("Hola mundo desde mi componente 2");
        log.error("Es un log");
    }
}
