package com.cooper.presigned_url.service;

import java.io.IOException;
import java.net.URL;

import org.springframework.web.multipart.MultipartFile;

public interface FileHandler {
	URL createSignedUrl(Long postId, String path);
	URL getSignedUrl(Long postId, String path);
	URL uploadFile(Long id, MultipartFile file) throws IOException;
}
