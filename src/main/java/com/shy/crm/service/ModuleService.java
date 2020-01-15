package com.shy.crm.service;

import com.shy.base.BaseService;
import com.shy.crm.dao.ModuleMapper;
import com.shy.crm.dao.PermissionMapper;
import com.shy.crm.dto.TreeDto;
import com.shy.crm.utils.AssertUtil;
import com.shy.crm.vo.Module;
import com.shy.crm.vo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author suhongyv
 */
@Service
@SuppressWarnings("all")
public class ModuleService extends BaseService<Module, Integer> {
    @Resource
    private ModuleMapper moduleMapper;
    @Resource
    private PermissionMapper permissionMapper;

    public List<TreeDto> queryAllModules(Integer roleId) {
        //查询所有的module
        List<TreeDto> treeDtoList = moduleMapper.queryAllModules();
        //在t_permission查询roleId下的所有可操作module
        List<Integer> roleHasMids = permissionMapper.queryRoleHasAllModulesByRoleId(roleId);
        //拿treeDtolist中的元素去roleHasMids遍历,如果存在就设置checked=true
        if (roleHasMids != null && roleHasMids.size() > 0) {
            treeDtoList.forEach(treeDto -> {
                if (roleHasMids.contains(treeDto.getId())) {
                    treeDto.setChecked(true);
                }
            });
        }
        return treeDtoList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveModule(Module module) {
        //菜单名不能为空
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()), "菜单名不可为空!!!!");
        Integer grade = module.getGrade();
        //菜单层级只能为0,1,2
        AssertUtil.isTrue(grade == null || !(grade == 0 || grade == 1 || grade == 2), "菜单层级不合法!!!");
        AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeAndModuleName(grade, module.getModuleName()), "该层级下存在重复菜单名");
        if (grade == 1) {
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()), "请指定二级菜单url");
            AssertUtil.isTrue(null != moduleMapper.queryModuleByGradeAndUrl(grade, module.getUrl()), "二级菜单Url不可重复");
        }
        if (grade != 0) {
            AssertUtil.isTrue(module.getParentId() == null || selectByPrimaryKey(module.getParentId()) == null, "请指定上级菜单");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()), "请输入权限码");
        AssertUtil.isTrue(null != moduleMapper.queryModuleByOpValue(module.getOptValue()), "权限码重复");
        module.setIsValid((byte) 1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(module) < 1, "添加失败!!!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateModule(Module module) {
        AssertUtil.isTrue(module.getId()==null||selectByPrimaryKey(module.getId())==null,"待更新记录不存在");
        Integer grade=module.getGrade();
        AssertUtil.isTrue(grade==null||!(grade==0||grade==1||grade==2),"菜单层级不合法!!!");
        Module temp=moduleMapper.queryModuleByGradeAndModuleName(grade,module.getModuleName());
        AssertUtil.isTrue(temp!=null&&!(module.getId().equals(temp.getId())),"该层级下存在重复菜单名");
        if(grade==1){
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"请指定二级菜单url");
            temp=moduleMapper.queryModuleByGradeAndUrl(grade,module.getUrl());
            AssertUtil.isTrue(temp!=null&&!(module.getId().equals(temp.getId())),"该层级下此url已存在");
        }
        if(grade!=0){
            Integer parentId = module.getParentId();
            AssertUtil.isTrue(parentId==null||selectByPrimaryKey(parentId)==null,"请指定上级菜单");
        }
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"请输入权限码");
        temp=moduleMapper.queryModuleByOpValue(module.getOptValue());
        AssertUtil.isTrue(temp!=null&&!(module.getId().equals(temp.getId())),"权限码重复,请重新输入");
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(module)<1,"菜单更新失败!");
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteModule(Integer id) {
        Module temp = moduleMapper.selectByPrimaryKey(id);
       AssertUtil.isTrue(id==null||temp==null,"待删除记录不存在");
       int count=moduleMapper.countSubModByParentId(id);
       AssertUtil.isTrue(count>0,"该菜单下存在子菜单,不能删除");
       count=permissionMapper.countPermissionByModuleId(id);
       if(count>0){
           AssertUtil.isTrue(permissionMapper.deletePermissionByModuleId(id)<count,"菜单删除失败");
       }
       temp.setIsValid((byte)0);
       AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"菜单删除失败");
    }
    public List<Map<String,Object>> queryAllModulesByGrade(Integer grade){
        return moduleMapper.queryAllModulesByGrade(grade);
    }
}
