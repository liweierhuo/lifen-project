package com.lifen.service;

import com.lifen.dataobject.TcScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TcScoreService {

    TcScore save(TcScore tcScore);

    void delete(Long scoreId);

    TcScore findByTaskId(Long taskId);

    TcScore findById(Long scoreId);

    Page<TcScore> getScoreList(PageRequest pageRequest, TcScore tcScore);
}
