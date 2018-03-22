package com.lifen.service;

import com.lifen.dataobject.TcTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TcTaskService {

    TcTask save(TcTask tcTask);

    void delete(Long taskId);

    boolean logicDelete(Long taskId);

    Page<TcTask> findByProjectId(Long projectId,PageRequest pageRequest);

    TcTask findById(Long taskId);

    Page<TcTask> getTaskList(PageRequest pageRequest, TcTask tcTask);
}
