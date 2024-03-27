package com.cooper.springweb.images;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class ImageCachingController {

	@GetMapping("/image-normal")
	public String imageNormal() {
		return "image/image-normal";
	}

	@GetMapping("/image-cache")
	public String imageCaching() {
		return "image/image-caching";
	}

}
