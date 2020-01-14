package com.shy.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shy.base.BaseService;
import com.shy.crm.enums.DevResult;
import com.shy.crm.enums.StateStatus;
import com.shy.crm.query.SaleChanceQuery;
import com.shy.crm.utils.AssertUtil;
import com.shy.crm.utils.PhoneUtil;
import com.shy.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suhongyv
 */
@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {
    /**
     * 查询营销机会列表,返回map
     * @param saleChanceQuery
     * @return
     */
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery){
        Map<String,Object> map=new HashMap<>();
        //开启分页
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getRows());
        //查询营销机会列表
        PageInfo<SaleChance> saleChancePageInfo = new PageInfo<>(selectByParams(saleChanceQuery));
        //把总数据数与机会列表放入到map中
        map.put("total",saleChancePageInfo.getTotal());
        map.put("rows",saleChancePageInfo.getList());
        return map;
    }

    /**
     * 添加机会记录
     * @param saleChance
     */
    public void saveSaleChance(SaleChance saleChance){
        //参数校验
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //为分配状态和开发状态设置默认值,用的是两个枚举类
        saleChance.setDevResult(DevResult.UNDEV.getType());
        saleChance.setState(StateStatus.UNSTATE.getType());
        //当指派人不为空时,设置分配状态和开发状态以及指派时间
        if(StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setDevResult(DevResult.DEVING.getType());
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setAssignTime(new Date());
        }
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        //添加数据,当受影响行数小于0时,返回错误信息
        AssertUtil.isTrue(insertSelective(saleChance)<1,"机会数据添加失败!!!");
    }

    /**
     * 参数校验方法
     * @param customerName
     * @param linkMan
     * @param linkPhone
     */
    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"请输入客户名!!");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"请输入联系人名!!");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"请输入联系电话!!");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(linkPhone)),"请输入正确的电话!!!");
    }

    /**
     * 更新营销机会记录
     * @param saleChance
     */
    public void updateSaleChance(SaleChance saleChance){
        //校验待更新的记录是否有效
        AssertUtil.isTrue(saleChance.getId()==null,"待更新记录不存在!!");
        SaleChance temp=selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(temp==null,"待更新记录不存在!!");
        //参数校验
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        //数据库记录中指派人不存在,要为本条数据添加指派人
        if(StringUtils.isBlank(temp.getAssignMan())&&StringUtils.isNotBlank(saleChance.getAssignMan())){
            saleChance.setAssignTime(new Date());
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getType());
        }
        //数据库记录中存在指派人,删除指派人
        else if(StringUtils.isNotBlank(temp.getAssignMan())&&StringUtils.isBlank(saleChance.getAssignMan())){
            saleChance.setAssignMan("");
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setDevResult(DevResult.UNDEV.getType());
            saleChance.setAssignTime(null);
        }
        //更新
        AssertUtil.isTrue(updateByPrimaryKeySelective(saleChance)<1,"更新失败!!!");
    }

    /**
     * 删除营销机会记录
     * @param ids
     */
    public void deleteSaleChance(Integer[] ids){
        AssertUtil.isTrue(ids==null&&ids.length==0,"请选择带删除记录!!!");
        AssertUtil.isTrue(deleteBatch(ids)<ids.length,"删除失败");
    }

    /**
     * 更新开发结果
     * @param saleChance
     */
    public void updateDevResult(SaleChance saleChance) {
        AssertUtil.isTrue(saleChance.getId()==null,"待更新记录不存在!!!");
        SaleChance temp=selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(temp==null,"待更新记录不存在!!!");
        AssertUtil.isTrue(temp.getState()==0||temp.getDevResult()!=1,"系统异常!!!");
        AssertUtil.isTrue(updateByPrimaryKeySelective(saleChance)<1,"更新失败!!!");

    }
}
