package com.cooper.springweb;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

public class AntPathMatcherTest {

	/**
	 * <pre>
	 *     ** : Match 0 or more directories
	 *     * : Match 0 or more characters
	 *     ? : Match any single character
	 * </pre>
	 *
	 * @see <a href="https://www.springcloud.io/post/2022-09/springmvc-pathpattern/#gsc.tab=0">[springcloud.io] springmvc pathpattern </a>
	 * @see <a href="https://javacan.tistory.com/entry/Tip-Using-Spring-AntPathMatcher">[javacan] Tip Using Spring AntPathMatcher </a>
	 *
	 */
	@Test
	@DisplayName("* 은 한 계층의 와일드카드 역할을 한다")
	void singleAsteriskWildcardTest() {
		AntPathMatcher pathMatcher = new AntPathMatcher();

		assertThat(pathMatcher.extractPathWithinPattern("/a/*", "/a/1")).isEqualTo("1");
		assertThat(pathMatcher.extractPathWithinPattern("/a/*", "/a/1/b")).isEqualTo("1/b");
		assertThat(pathMatcher.extractPathWithinPattern("/a/b/*", "/a/b/c")).isEqualTo("c");
		assertThat(pathMatcher.extractPathWithinPattern("/a/b/*.xml", "/a/b/c.xml")).isEqualTo("c.xml");

		assertThat(pathMatcher.extractPathWithinPattern("/*", "/a/b/c.xml")).isEqualTo("a/b/c.xml");
		assertThat(pathMatcher.extractPathWithinPattern("/*.xml", "/a/b/c.xml")).isEqualTo("a/b/c.xml");

		assertThat(pathMatcher.extractPathWithinPattern("/a/*", "/a/1/b")).isEqualTo("1/b");
		assertThat(pathMatcher.extractPathWithinPattern("/a/*", "/a/1/b.xml")).isEqualTo("1/b.xml");
	}

	@Test
	@DisplayName("** 은 여러 계층의 와일드카드 역할을 한다")
	void doubleAsteriskWildcardTest() {
		AntPathMatcher pathMatcher = new AntPathMatcher();

		assertThat(pathMatcher.extractPathWithinPattern("/a/*", "/a/1/b")).isEqualTo("1/b");
		assertThat(pathMatcher.extractPathWithinPattern("/a/**", "/a/1/b")).isEqualTo("1/b");
	}

}
