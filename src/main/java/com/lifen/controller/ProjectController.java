package com.lifen.controller;

import com.lifen.constant.ProjectConstant;
import com.lifen.dataobject.TaProject;
import com.lifen.dataobject.TcTask;
import com.lifen.dataobject.UcUsers;
import com.lifen.dto.ProjectBo;
import com.lifen.enums.IsDeletedEnum;
import com.lifen.enums.ProjectStatusEnum;
import com.lifen.enums.ProjectTypeEnum;
import com.lifen.enums.UserTypeEnum;
import com.lifen.service.TaProjectService;
import com.lifen.service.TcTaskService;
import com.lifen.utils.PageResult;
import com.lifen.utils.ResultMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Controller
@Slf4j
public class ProjectController {

    @Autowired
    private TaProjectService taProjectService;
    @Autowired
    private TcTaskService tcTaskService;

    @RequestMapping("project/project_list")
    public String getProjectList(@RequestParam(value = "page",required = false) Integer page,
                                 @RequestParam(value = "limit",required = false) Integer limit, Model model,
                                 @ModelAttribute("taProject") TaProject taProject,HttpServletRequest request){
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        if (taProject == null) {
            taProject = new TaProject();
        }
        PageRequest pageRequest = new PageRequest(page - 1,limit);
        taProject.setIsDeleted(IsDeletedEnum.NO.getCode());
        Page<TaProject> result = taProjectService.getProjectList(pageRequest,taProject);
        PageResult<TaProject> projectList = new PageResult<TaProject>(result.getTotalElements(),result.getContent());
        projectList.setPages(result.getTotalPages());
        projectList.setCurrentPage(page);
        model.addAttribute("projectList",projectList);
        model.addAttribute("taProject",taProject);
        model.addAttribute("projectTypeMap", ProjectTypeEnum.toMap());
        return "views/project_list";
    }

    @ResponseBody
    @GetMapping("admin/project_list")
    public PageResult<TaProject> getProjectListJson(@RequestParam(value = "page",required = false) Integer page,
                                 @RequestParam(value = "limit",required = false) Integer limit, Model model,
                                 @RequestParam(value = "taProject",required = false) TaProject taProject){
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        if (taProject == null) {
            taProject = new TaProject();
        }
        PageRequest pageRequest = new PageRequest(page - 1,limit);
        taProject.setIsDeleted(IsDeletedEnum.NO.getCode());
        Page<TaProject> result = taProjectService.getProjectList(pageRequest,taProject);
        PageResult<TaProject> projectList = new PageResult<TaProject>(result.getTotalElements(),result.getContent());
        model.addAttribute("projectList",projectList);
        return projectList;
    }

    @GetMapping("project/project_detail/{projectCode}")
    public String getTeacherList(@PathVariable("projectCode") String projectCode,Model model){
        if (StringUtils.isEmpty(projectCode)) {
            return "views/error";
        }
        TaProject project = taProjectService.findByProjectCode(projectCode);
        PageRequest pageRequest = new PageRequest(0,10);
        TcTask tcTaskQuery = new TcTask();
        tcTaskQuery.setProjectId(project.getProjectId());
        tcTaskQuery.setIsDeleted(IsDeletedEnum.NO.getCode());
        Page<TcTask> taskResult = tcTaskService.getTaskList(pageRequest,tcTaskQuery);
        ProjectBo projectBo = new ProjectBo();
        BeanUtils.copyProperties(project,projectBo);
        projectBo.setTaskPage(taskResult);
        model.addAttribute("project",projectBo);
        model.addAttribute("projectTypeMap", ProjectTypeEnum.toMap());
        return "views/project_detail";
    }

    @GetMapping("project/add_project")
    public String addProjectInit(Model model){
        return "views/add_project";
    }



    @ResponseBody
    @PostMapping("project/add_project")
    public ResultMap addProject(TaProject taProject){
        taProject.setCreateTime(new Date());
        taProject.setUpdateTime(new Date());
        taProject.setProjectCode(UUID.randomUUID().toString());
        taProject.setIsDeleted(IsDeletedEnum.NO.getCode());
        taProject.setProjectStatus(ProjectStatusEnum.NORMAL.getCode());
        TaProject taProjectResult = taProjectService.save(taProject);
        if (taProjectResult != null) {
            return ResultMap.ok("操作成功，去看看吧");
        } else {
            return ResultMap.error();
        }
    }
}
