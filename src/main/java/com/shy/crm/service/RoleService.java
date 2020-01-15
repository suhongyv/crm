package com.shy.crm.service;

import com.shy.base.BaseService;
import com.shy.crm.dao.ModuleMapper;
import com.shy.crm.dao.PermissionMapper;
import com.shy.crm.dao.RoleMapper;
import com.shy.crm.utils.AssertUtil;
import com.shy.crm.vo.Permission;
import com.shy.crm.vo.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author suhongyv
 */
@Service
public class RoleService extends BaseService<Role,Integer> {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private ModuleMapper moduleMapper;
    /**
     * 查询所有的角色
     * @return
     */
    public List<Map<String,Object>> queryAllRoles(){
       return roleMapper.queryAllRoles();
    }

    /**
     * 添加角色
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRole(Role role){
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名!!");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(temp!=null,"该角色已存在!!");
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(role)<1,"角色记录添加失败!!!");
    }

    /**
     * 更新角色
     * @param role
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRole(Role role){
        AssertUtil.isTrue(null==role.getId()||selectByPrimaryKey(role.getId())==null,"待修改的记录不存在!!!");
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"请输入角色名!!");
        Role temp = roleMapper.queryRoleByRoleName(role.getRoleName());
        AssertUtil.isTrue(null!=temp&&!(role.getId().equals(temp.getId())),"该角色已存在!!!");
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(role)<1,"更新失败!!!");
    }

    /**
     * 删除角色
     * @param roleId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRole(Integer roleId){
        Role role=selectByPrimaryKey(roleId);
        AssertUtil.isTrue(null==roleId||role==null,"待删除的记录不存在!!!");
        role.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(role)<1,"删除失败!!!");
    }

    /**
     * 角色资源授权
     * @param mids
     * @param roleId
     */
    public void addGrant(Integer[] mids, Integer roleId) {
        //参数验证
        Role temp = selectByPrimaryKey(roleId);
        AssertUtil.isTrue(temp==null|roleId==null,"待授权的角色不存在!!!");
        //获取角色可操作的资源数目
        int count=permissionMapper.countPermissionByRoleId(roleId);
        //当角色下资源数目大于零时,先执行删除操作,再执行分配操作
        if(count>0){
            AssertUtil.isTrue(permissionMapper.deletePermissionByRoleId(roleId)<count,"权限分配失败!!!");
        }
        //初始化授权
        if(mids!=null&&mids.length>0){
            List<Permission> permissionList=new ArrayList<>();
            for (Integer mid:mids) {
                Permission permission=new Permission();
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permission.setModuleId(mid);
                permission.setRoleId(roleId);
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mid).getOptValue());
                permissionList.add(permission);
            }
            //添加授权信息
            permissionMapper.insertBatch(permissionList);
        }
    }
}
