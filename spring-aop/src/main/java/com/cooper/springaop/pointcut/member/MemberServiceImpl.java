package com.cooper.springaop.pointcut.member;

import com.cooper.springaop.pointcut.member.annotation.MethodAop;

public class MemberServiceImpl implements MemberService {

	@Override
	@MethodAop("test value")
	public String hello(String param) {
		return "ok";
	}

	public String internal(String param) {
		return "ok";
	}
}
