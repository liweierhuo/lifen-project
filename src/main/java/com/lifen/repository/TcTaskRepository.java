package com.lifen.repository;

import com.lifen.dataobject.TcTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TcTaskRepository extends JpaRepository<TcTask, Long> {

    Page<TcTask> findAll(Specification<TcTask> spec, Pageable pageable);

    Page<TcTask> findByProjectId(Long projectId,Pageable pageable);

}
