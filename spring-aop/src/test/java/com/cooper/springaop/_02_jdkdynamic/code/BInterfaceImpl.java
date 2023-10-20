package com.cooper.springaop._02_jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BInterfaceImpl implements BInterface {
	@Override
	public String call() {
		log.info("B 호출");
		return "B";
	}
}
