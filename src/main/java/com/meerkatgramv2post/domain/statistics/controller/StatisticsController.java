package com.meerkatgramv2post.domain.statistics.controller;

import com.meerkatgramv2post.domain.statistics.response.UserPostCountResponseDTO;
import com.meerkatgramv2post.domain.statistics.service.StatisticService;
import com.meerkatgramv2post.global.response.GlobalResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시글 통계", description = "게시글 통계 관련")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/statistics")
public class StatisticsController {
  private final StatisticService statisticService;

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/user-post-count")
  public ResponseEntity<GlobalResponseDTO<UserPostCountResponseDTO>> getUserPostCount(AutoCloseable) {
    long userId = Long.parseLong(authentication.getName());
    return ResponseEntity.ok(GlobalResponseDTO.success(statisticService.getUserPostCount(userId)));
  }
}
