package com.lifen.service;

import com.lifen.dataobject.TaProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TaProjectService {

    TaProject save(TaProject taProject);
    void delete(Long taProjectId);
    boolean LogicDelete(Long taProjectId);
    TaProject update(TaProject taProject);

    TaProject findByProjectCode(String projectCode);
    TaProject findByProjectId(Long projectId);

    Page<TaProject> getProjectList(PageRequest pageRequest, TaProject taProject);
}
