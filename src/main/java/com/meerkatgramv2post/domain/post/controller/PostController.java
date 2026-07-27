package com.meerkatgramv2post.domain.post.controller;

import com.meerkatgramv2post.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping
    public String index(
        Authentication authentication
    ) {
        return String.format("%s / %s", authentication.getName(), authentication.getAuthorities());
    }
}