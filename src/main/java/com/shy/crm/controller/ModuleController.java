package com.shy.crm.controller;

import com.shy.base.BaseController;
import com.shy.crm.dto.TreeDto;
import com.shy.crm.model.ResultInfo;
import com.shy.crm.query.ModuleQuery;
import com.shy.crm.service.ModuleService;
import com.shy.crm.vo.Module;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author suhongyv
 */
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {
    @Resource
    private ModuleService moduleService;
    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeDto> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }
    @RequestMapping("/index/{grade}")
    public String index(@PathVariable Integer grade, Integer mid, Model model){
        model.addAttribute("mid",mid);
        if(grade==1){
            return "module_1";
        }else if(grade==2){
            return "module_2";
        }else if(grade==3){
            return "module_3";
        }else {
            return "";
        }
    }
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queModulesByParams(ModuleQuery moduleQuery){
        Map<String, Object> stringObjectMap = moduleService.queryByParamsForDataGrid(moduleQuery);
        return stringObjectMap;
    }
    @ResponseBody
    @RequestMapping("save")
    public ResultInfo saveModule(Module module){
        moduleService.saveModule(module);
        return success();
    }
    @ResponseBody
    @RequestMapping("update")
    public ResultInfo updateModule(Module module){
        moduleService.updateModule(module);
        return success();
    }
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteModule(Integer id){
        moduleService.deleteModule(id);
        return success();
    }
    @ResponseBody
    @RequestMapping("queryAllModulesByGrade")
    public List<Map<String,Object>> queryAllModulesByGrade(Integer grade){
            return moduleService.queryAllModulesByGrade(grade);
    }
}
