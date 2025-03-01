package com.woori.codeshare.comment.repository;

import com.woori.codeshare.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.snapshot.snapshotId = :snapshotId")
    List<Comment> findBySnapshotId(@Param("snapshotId") Long snapshotId);
}
