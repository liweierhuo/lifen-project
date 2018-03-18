package com.lifen.service.impl;

import com.lifen.dataobject.TaProject;
import com.lifen.repository.TaProjectRepository;
import com.lifen.service.TaProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaProjectServiceImpl implements TaProjectService {

    @Autowired
    private TaProjectRepository taProjectRepository;


    @Override
    public TaProject save(TaProject taProject) {
        return taProjectRepository.save(taProject);
    }

    @Override
    public void delete(Long taProjectId) {
        taProjectRepository.delete(taProjectId);
    }

    @Override
    public TaProject findByProjectCode(String projectCode) {
        return taProjectRepository.findByProjectCode(projectCode);
    }

    @Override
    public Page<TaProject> getProjectList(PageRequest pageRequest, TaProject taProject) {
        Pageable pageable = new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.Direction.DESC, "projectId");
        Specification<TaProject> userSpec = new Specification<TaProject>() {
            @Override
            public Predicate toPredicate(Root<TaProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                if(null!=taProject.getIsDeleted()&&!"".equals(taProject.getIsDeleted())){
                    list.add(criteriaBuilder.equal(root.get("isDeleted").as(String.class), taProject.getIsDeleted()));
                }
                if(null!=taProject.getUserAccount()&&!"".equals(taProject.getUserAccount())){
                    list.add(criteriaBuilder.equal(root.get("userAccount").as(String.class), taProject.getUserAccount()));
                }
                if(null!=taProject.getProjectName()&&!"".equals(taProject.getProjectName())){
                    list.add(criteriaBuilder.like(root.get("projectName").as(String.class), taProject.getProjectName()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        return taProjectRepository.findAll(userSpec, pageable);
    }
}
