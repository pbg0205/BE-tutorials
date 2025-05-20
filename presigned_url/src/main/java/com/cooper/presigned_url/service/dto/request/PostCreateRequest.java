package com.cooper.presigned_url.service.dto.request;

public record PostCreateRequest(Long id, String title, String content, String fileName) {
}
