package com.shy.crm.controller;

import com.shy.base.BaseController;
import com.shy.crm.dto.TreeDto;
import com.shy.crm.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;
    @RequestMapping("module/queryAllModules")
    @ResponseBody
    public List<TreeDto> queryAllModules(){
        return moduleService.queryAllModules();
    }
}
