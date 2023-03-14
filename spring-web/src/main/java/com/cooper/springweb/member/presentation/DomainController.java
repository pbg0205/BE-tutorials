package com.cooper.springweb.member.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class DomainController {

    // locale, domain 정규식 매핑 테스트
    @GetMapping(value = {
            "{locale:ko|ja|en}/{domain:^(?!version.txt|ko|ja|en)[a-zA-Z0-9.@]*$}",
            "{domain:^(?!version.txt|ko|ja|en)[a-zA-Z0-9.@]*$}",
            "/{locale:ko|ja|en}",
    })
    public String memberPage(
            @PathVariable(required = false) String domain,
            @PathVariable(required = false) String locale) {

        log.debug("domain: {}", domain);
        log.debug("locale: {}", locale);

        return "index";
    }

}
