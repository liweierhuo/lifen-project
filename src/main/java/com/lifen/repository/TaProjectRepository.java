package com.lifen.repository;

import com.lifen.dataobject.TaProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaProjectRepository extends JpaRepository<TaProject, Long> {

    Page<TaProject> findAll(Specification<TaProject> spec, Pageable pageable);

    TaProject findByProjectCode(String projectCode);

}
