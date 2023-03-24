package com.cooper.springweb;

import static org.assertj.core.api.Assertions.*;

import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

public class PathPatternTest {

	/**
	 * @see <a href="https://www.springcloud.io/post/2022-09/springmvc-pathpattern/#gsc.tab=0">[springcloud.io] springmvc pathpattern </a>
	 */
	@Test
	@DisplayName("AntPathMatcher 는 PathPattern 이 중복될 경우, antPathMatcher 가 우선적으로 매핑된다")
	void antPathMatcherPathPatternTest() {
		PathPattern pattern1 = PathPatternParser.defaultInstance.parse("/api/yourbatman/{*pathVariable}");
		PathPattern pattern2 = PathPatternParser.defaultInstance.parse("/api/yourbatman/**");

		SortedSet<PathPattern> sortedSet = new TreeSet<>();
		sortedSet.add(pattern1);
		sortedSet.add(pattern2);

		PathPattern firstPattern = sortedSet.first();

		assertThat(pattern2).isEqualTo(firstPattern);
	}

}
