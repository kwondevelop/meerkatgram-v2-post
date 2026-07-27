package com.meerkatgramv2post.domain.post.repository;

import com.meerkatgramv2post.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.meerkatgramv2post.domain.post.entity.QPost.post;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryDSLRepository {
  private final JPAQueryFactory jpaQueryFactory;

  public List<Post> pagination(long offset, long limit) {
    return jpaQueryFactory
        .selectFrom(post)
        .orderBy(post.createdAt.desc(), post.id.desc())
        .limit(limit)
        .offset(offset)
        .fetch()
        ;
  }
}
