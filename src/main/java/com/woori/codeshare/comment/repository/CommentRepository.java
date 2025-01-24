package com.woori.codeshare.comment.repository;

import com.woori.codeshare.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
