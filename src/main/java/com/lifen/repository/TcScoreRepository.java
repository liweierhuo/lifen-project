package com.lifen.repository;

import com.lifen.dataobject.TcScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TcScoreRepository extends JpaRepository<TcScore, Long> {
    Page<TcScore> findAll(Specification<TcScore> spec, Pageable pageable);

    TcScore findByTaskId(Long taskId);


}
