package com.meerkatgramv2post.domain.file.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record FileResponseDTO(
    @Schema(name = "이미지 경로", requiredMode = Schema.RequiredMode.REQUIRED)
    String fileUri
) {
    public static FileResponseDTO from(String fileUri) {
      return new FileResponseDTO(fileUri);
    }
}
