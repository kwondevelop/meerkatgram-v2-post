package com.msa4meerkatgramv2post.domain.statistics.service;

import com.msa4meerkatgramv2post.domain.statistics.repository.StatisticsRepository;
import com.msa4meerkatgramv2post.domain.statistics.response.UserPostCountResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    public UserPostCountResponseDTO getUserPostCount(long userId) {
        return UserPostCountResponseDTO.from(statisticsRepository.countByUserId(userId));
    }
}
