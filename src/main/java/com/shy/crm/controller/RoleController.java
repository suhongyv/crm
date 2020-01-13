package com.shy.crm.controller;

import com.shy.base.BaseController;
import com.shy.crm.model.ResultInfo;
import com.shy.crm.query.RoleQuery;
import com.shy.crm.service.RoleService;
import com.shy.crm.vo.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author suhongyv
 */
@Controller
public class RoleController extends BaseController {
    @Resource
    private RoleService roleService;

    /**
     * 查询所有的角色
     * @return
     */
    @RequestMapping("role/queryAllRoles")
    @ResponseBody
    public List<Map<String,Object>> queryAllRoles(){
        return roleService.queryAllRoles();
    }
    @RequestMapping("role/index")
    public String index(){
        return "role";
    }

    /**
     * 角色列表
     * @param roleQuery
     * @return
     */
    @RequestMapping("role/list")
    @ResponseBody
    public Map<String,Object> queryRolesByParams(RoleQuery roleQuery){
        return roleService.queryByParamsForDataGrid(roleQuery);
    }
    @ResponseBody
    @RequestMapping("role/save")
    public ResultInfo saveRole(Role role){
        roleService.saveRole(role);
        return success("添加成功");
    }
    @ResponseBody
    @RequestMapping("role/update")
    public ResultInfo updateRole(Role role){
        roleService.updateRole(role);
        return success("更新成功");
    }
    @ResponseBody
    @RequestMapping("role/delete")
    public ResultInfo deleteRole(Integer id){
        roleService.deleteRole(id);
        return success("删除成功");
    }
}
