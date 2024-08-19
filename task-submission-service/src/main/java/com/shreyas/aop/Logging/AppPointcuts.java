package com.shreyas.aop.Logging;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class AppPointcuts {

    @Pointcut("within(com.shreyas.controller..*)")
    public void controllerPointcuts() {
    }

    @Pointcut("within(com.shreyas.service..*)")
    public void servicePointcuts() {
    }

    @Pointcut("within(com.shreyas.repository..*)")
    public void repositoryPointcuts() {
    }

    @Pointcut("controllerPointcuts() || servicePointcuts() || repositoryPointcuts()")
    public void mainPointcuts() {
    }
}
