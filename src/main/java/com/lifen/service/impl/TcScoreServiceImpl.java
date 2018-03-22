package com.lifen.service.impl;

import com.lifen.dataobject.TcScore;
import com.lifen.dataobject.TcTask;
import com.lifen.repository.TcScoreRepository;
import com.lifen.repository.TcTaskRepository;
import com.lifen.service.TcScoreService;
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
public class TcScoreServiceImpl implements TcScoreService {
    @Autowired
    private TcScoreRepository tcScoreRepository;
    @Override
    public TcScore save(TcScore tcScore) {
        return tcScoreRepository.save(tcScore);
    }

    @Override
    public void delete(Long scoreId) {
        tcScoreRepository.delete(scoreId);
    }

    @Override
    public TcScore findByTaskId(Long taskId) {
        return tcScoreRepository.findByTaskId(taskId);
    }

    @Override
    public TcScore findById(Long scoreId) {
        return tcScoreRepository.findOne(scoreId);
    }

    @Override
    public Page<TcScore> getScoreList(PageRequest pageRequest, TcScore tcScore) {
        Pageable pageable = new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.Direction.DESC, "taskId");
        Specification<TcScore> spec = new Specification<TcScore>() {
            @Override
            public Predicate toPredicate(Root<TcScore> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                if(null!=tcScore.getIsDeleted()&&!"".equals(tcScore.getIsDeleted())){
                    list.add(criteriaBuilder.equal(root.get("isDeleted").as(String.class), tcScore.getIsDeleted()));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        };
        return tcScoreRepository.findAll(spec, pageable);
    }
}
