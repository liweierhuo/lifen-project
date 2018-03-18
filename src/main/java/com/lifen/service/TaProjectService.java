package com.lifen.service;

import com.lifen.dataobject.TaProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface TaProjectService {

    TaProject save(TaProject taProject);
    void delete(Long taProjectId);

    TaProject findByProjectCode(String projectCode);

    Page<TaProject> getProjectList(PageRequest pageRequest, TaProject taProject);
}
