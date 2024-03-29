package com.cooper.springwebcachecontrol.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image-sample")
public class ImageSampleController {

	@GetMapping
	public String imageNormal() {
		return "image-sample";
	}

}
