package com.lifen.controller;

import com.lifen.dataobject.TaProject;
import com.lifen.dataobject.TcScore;
import com.lifen.dataobject.TcTask;
import com.lifen.dto.TaskBo;
import com.lifen.enums.IsDeletedEnum;
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
public class ScoreController {

    @Autowired
    private TcTaskService tcTaskService;
    @Autowired
    private TaProjectService taProjectService;
    @Autowired
    private TcScoreService tcScoreService;



    @GetMapping("score/add_score")
    public String addInit(Model model,@RequestParam(value = "taskId",required = false) Long taskId){
        if (taskId != null) {
            return "error";
        }
        TcTask tcTask = tcTaskService.findById(taskId);
        if (tcTask == null) {
            return "error";
        }
        model.addAttribute("tcTask",tcTask);
        return "views/add_score";
    }

    @GetMapping("score/edit_score")
    public String editInit(Model model,@RequestParam(value = "scoreId",required = false) Long scoreId){
        if (scoreId != null) {
            return "error";
        }
        TcScore tcScore = tcScoreService.findById(scoreId);
        if (scoreId == null) {
            return "error";
        }
        model.addAttribute("tcScore",tcScore);
        return "views/eidt_score";
    }



    @ResponseBody
    @PostMapping("score/add_score")
    public ResultMap addScore(TcScore tcScore){
        if (StringUtils.isEmpty(tcScore.getUserAccount())) {
            return ResultMap.error("用户账号不能为空");
        }
        if (tcScore.getTaskId() == null) {
            return ResultMap.error("taskId不能为空");
        }
        tcScore.setCreateTime(new Date());
        tcScore.setUpdateTime(new Date());
        tcScore.setIsDeleted(IsDeletedEnum.NO.getCode());
        TcScore result = tcScoreService.save(tcScore);
        if (result != null) {
            return ResultMap.ok("操作成功，去看看吧");
        } else {
            return ResultMap.error();
        }
    }
}
