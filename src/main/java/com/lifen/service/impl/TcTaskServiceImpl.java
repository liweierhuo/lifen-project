package com.lifen.service.impl;

import com.lifen.dataobject.TaProject;
import com.lifen.dataobject.TcTask;
import com.lifen.repository.TcTaskRepository;
import com.lifen.service.TcTaskService;
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

/**
 * Created by liwei on 2018/3/19.
 */
@Service
@Slf4j
public class TcTaskServiceImpl implements TcTaskService {
    @Autowired
    private TcTaskRepository tcTaskRepository;
    @Override
    public TcTask save(TcTask tcTask) {
        return tcTaskRepository.save(tcTask);
    }

    @Override
    public void delete(Long taskId) {
        tcTaskRepository.delete(taskId);
    }

    @Override
    public Page<TcTask> findByProjectId(Long projectId,PageRequest pageRequest) {
        return tcTaskRepository.findByProjectId(projectId,pageRequest);
    }

    @Override
    public TcTask findById(Long taskId) {
        return tcTaskRepository.findOne(taskId);
    }

    @Override
    public Page<TcTask> getTaskList(PageRequest pageRequest, TcTask tcTask) {
        Pageable pageable = new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.Direction.DESC, "taskId");
        Specification<TcTask> userSpec = new Specification<TcTask>() {
            @Override
            public Predicate toPredicate(Root<TcTask> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                if(null!=tcTask.getIsDeleted()&&!"".equals(tcTask.getIsDeleted())){
                    list.add(criteriaBuilder.equal(root.get("isDeleted").as(String.class), tcTask.getIsDeleted()));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        return tcTaskRepository.findAll(userSpec, pageable);
    }
}
