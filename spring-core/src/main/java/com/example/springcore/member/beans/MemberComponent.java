package com.example.springcore.member.beans;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Log4j2
@Component
public class MemberComponent implements InitializingBean, DisposableBean, BeanNameAware {

    public MemberComponent() {
        log.debug("{} {}", MemberComponent.class.getName(), "생성자 초기화");
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
        log.debug("{} {} {}", MemberComponent.class.getName(), "setBeanName() 메서드 동작", name);
    }

}
