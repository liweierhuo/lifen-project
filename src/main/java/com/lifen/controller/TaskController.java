package com.lifen.controller;

import com.lifen.dataobject.TaProject;
import com.lifen.dataobject.TcScore;
import com.lifen.dataobject.TcTask;
import com.lifen.dto.TaskBo;
import com.lifen.enums.IsDeletedEnum;
import com.lifen.enums.ProjectStatusEnum;
import com.lifen.enums.ProjectTypeEnum;
import com.lifen.enums.TaskTypeEnum;
import com.lifen.service.TaProjectService;
import com.lifen.service.TcScoreService;
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

@Controller
@Slf4j
public class TaskController {

    @Autowired
    private TcTaskService tcTaskService;
    @Autowired
    private TaProjectService taProjectService;
    @Autowired
    private TcScoreService tcScoreService;

    @RequestMapping("task/task_list")
    public String getProjectList(@RequestParam(value = "page",required = false) Integer page,
                                 @RequestParam(value = "limit",required = false) Integer limit, Model model,
                                 @ModelAttribute("tcTask") TcTask tcTask, HttpServletRequest request){
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        if (tcTask == null) {
            tcTask = new TcTask();
        }
        PageRequest pageRequest = new PageRequest(page - 1,limit);
        tcTask.setIsDeleted(IsDeletedEnum.NO.getCode());
        Page<TcTask> result = tcTaskService.getTaskList(pageRequest,tcTask);
        PageResult<TcTask> taskList = new PageResult<TcTask>(result.getTotalElements(),result.getContent());
        taskList.setPages(result.getTotalPages());
        taskList.setCurrentPage(page);
        model.addAttribute("taskList",taskList);
        model.addAttribute("task",tcTask);
        model.addAttribute("taskTypeMap", TaskTypeEnum.toMap());
        return "views/task_list";
    }

    @ResponseBody
    @GetMapping("admin/task_list")
    public PageResult<TcTask> getProjectListJson(@RequestParam(value = "page",required = false) Integer page,
                                 @RequestParam(value = "limit",required = false) Integer limit, Model model,
                                 @RequestParam(value = "tcTask",required = false) TcTask tcTask){
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        if (tcTask == null) {
            tcTask = new TcTask();
        }
        PageRequest pageRequest = new PageRequest(page - 1,limit);
        tcTask.setIsDeleted(IsDeletedEnum.NO.getCode());
        Page<TcTask> result = tcTaskService.getTaskList(pageRequest,tcTask);
        PageResult<TcTask> taskList = new PageResult<TcTask>(result.getTotalElements(),result.getContent());
        model.addAttribute("taskList",taskList);
        return taskList;
    }

    @GetMapping("task/task_detail/{taskId}")
    public String getTeacherList(@PathVariable("taskId") Long taskId,Model model){
        if (taskId == null) {
            return "views/error";
        }
        TcTask tcTask = tcTaskService.findById(taskId);
        TcScore scoreResult = tcScoreService.findByTaskId(tcTask.getTaskId());
        TaProject project = taProjectService.findByProjectId(tcTask.getProjectId());
        TaskBo taskBo = new TaskBo();
        BeanUtils.copyProperties(tcTask, taskBo);
        taskBo.setProjectPublisher(project.getUserAccount());
        taskBo.setTcScore(scoreResult);
        model.addAttribute("taskBo",taskBo);
        model.addAttribute("taskTypeMap",TaskTypeEnum.toMap());
        return "views/task_detail";
    }

    @GetMapping("task/add_task")
    public String addProjectInit(Model model,@RequestParam(value = "projectCode",required = false) String projectCode){
        if (StringUtils.isEmpty(projectCode)) {
            return "error";
        }
        TaProject taProject = taProjectService.findByProjectCode(projectCode);
        if (taProject == null) {
            return "error";
        }
        model.addAttribute("taProject",taProject);
        return "views/add_task";
    }



    @ResponseBody
    @PostMapping("task/add_task")
    public ResultMap addProject(TcTask tcTask){
        if (StringUtils.isEmpty(tcTask.getUserAccount())) {
            return ResultMap.error("用户账号不能为空");
        }
        tcTask.setCreateTime(new Date());
        tcTask.setUpdateTime(new Date());
        tcTask.setIsDeleted(IsDeletedEnum.NO.getCode());
        TcTask result = tcTaskService.save(tcTask);
        if (result != null) {
            return ResultMap.ok("操作成功，去看看吧");
        } else {
            return ResultMap.error();
        }
    }
}
