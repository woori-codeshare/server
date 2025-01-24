package com.woori.codeshare.snapshot.repository;

import com.woori.codeshare.snapshot.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
