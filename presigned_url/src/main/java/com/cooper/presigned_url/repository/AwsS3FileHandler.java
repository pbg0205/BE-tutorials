package com.cooper.presigned_url.repository;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;

import com.cooper.presigned_url.service.FileHandler;

@Component
@RequiredArgsConstructor
public class AwsS3FileHandler implements FileHandler {

	@Value("${aws.bucket.name}")
	private String bucketName;

	private final S3Template s3Template;

	@Override
	public URL createSignedUrl(final Long postId, final String fileName) {
		final String filePath = String.format("%s/%s", postId, fileName);
		return s3Template.createSignedPutURL(bucketName, filePath, Duration.ofMinutes(1));
	}

	@Override
	public URL getSignedUrl(final Long postId, final String path) {
		String key = path.replaceFirst("^/", ""); // object key 는 path 앞에 / 를 제거해야 한다.
		return s3Template.createSignedGetURL(bucketName, key, Duration.ofMinutes(1));
	}

	@Override
	public URL uploadFile(final Long postId, final MultipartFile file) throws IOException {
		final String fileName = file.getOriginalFilename();
		final String filePath = String.format("%s/%s", postId, fileName);
		final S3Resource s3Resource = s3Template.upload(bucketName, filePath, file.getInputStream());
		return s3Resource.getURL();
	}
}
