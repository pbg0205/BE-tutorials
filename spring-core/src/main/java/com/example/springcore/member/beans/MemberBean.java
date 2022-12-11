package com.example.springcore.member.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
public class MemberBean implements InitializingBean, DisposableBean, BeanNameAware {

    public MemberBean() {
        log.debug("{} {}", MemberBean.class.getName(), "생성자 초기화");
    }

    @PostConstruct
    public void postConstruct() {
        //빈 초기화 시, 작동하는 비즈니스 로직
        log.debug("{} {}", MemberBean.class.getName(), "@postConstruct 메서드 동작");
    }

    @PreDestroy
    public void preDestroy() {
        //빈 초기화 시, 작동하는 비즈니스 로직
        log.debug("{} {}", MemberBean.class.getName(), "@preDestroy 메서드 동작");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //빈 초기화 시, 작동하는 비즈니스 로직
        log.debug("{} {}", MemberComponent.class.getName(), "afterPropertiesSet() 메서드 동작");
    }

    @Override
    public void destroy() throws Exception {
        //빈 소멸 시, 작동하는 비즈니스 로직
        log.debug("{} {}", MemberComponent.class.getName(), "destroy() 메서드 동작");
    }

    @Override
    public void setBeanName(String name) {
        log.debug("{} {} {}", MemberBean.class.getName(), "setBeanName() 메서드 동작", name);
    }

    private void initMethod() {
        log.debug("{} {}", MemberBean.class.getName(), "initMethod() 메서드 동작");
    }

    private void destroyMethod() {
        log.debug("{} {}", MemberBean.class.getName(), "destroyMethod() 메서드 동작");
    }
}
