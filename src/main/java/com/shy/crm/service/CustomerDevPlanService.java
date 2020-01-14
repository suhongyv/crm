package com.shy.crm.service;

import com.shy.base.BaseService;
import com.shy.crm.dao.SaleChanceMapper;
import com.shy.crm.utils.AssertUtil;
import com.shy.crm.vo.CustomerDevPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author suhongyv
 */
@Service
public class CustomerDevPlanService extends BaseService<CustomerDevPlan,Integer> {
    @Resource
    private SaleChanceMapper saleChanceMapper;
    /**
     * 添加用户开发计划
     * @param customerDevPlan
     */
    public void saveCustomerDevPlan(CustomerDevPlan customerDevPlan){
        /**
         * 1.参数校验
         *     营销机会id 非空 记录必须存在
         *     计划项内容  非空
         *     计划项时间 非空
         * 2.参数默认值设置
         *    is_valid createDate  updateDate
         *
         *  3.执行添加  判断结果
         */
        checkParams(customerDevPlan.getSaleChanceId(),customerDevPlan.getPlanItem(),customerDevPlan.getPlanDate());
        customerDevPlan.setIsValid(1);
        customerDevPlan.setCreateDate(new Date());
        customerDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(customerDevPlan)<1,"添加失败!!");
    }

    private void checkParams(Integer saleChanceId, String planItem, Date planDate) {
        AssertUtil.isTrue(saleChanceId==null||saleChanceMapper.selectByPrimaryKey(saleChanceId)==null,"请设置营销机会id");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"请输入计划项内容!!");
        AssertUtil.isTrue(planDate==null,"请指定计划项日期!!");
    }

    /**
     * 更新用户开发计划
     * @param customerDevPlan
     */
    public void updateCustomerDevPlan(CustomerDevPlan customerDevPlan){
        /**
         * 1.参数校验
         *     id  非空 记录存在
         *     营销机会id 非空 记录必须存在
         *     计划项内容  非空
         *     计划项时间 非空
         * 2.参数默认值设置
         updateDate
         *  3.执行更新  判断结果
         */
        AssertUtil.isTrue(customerDevPlan.getId()==null||selectByPrimaryKey(customerDevPlan.getId())==null,"待更新记录不存在");
        checkParams(customerDevPlan.getSaleChanceId(),customerDevPlan.getPlanItem(),customerDevPlan.getPlanDate());
        customerDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerDevPlan)<1,"更新失败!!!");
    }

    /**
     * 删除用户开发计划
     * @param id
     */
    public void deleteCustomerDevPlan(Integer id){
        CustomerDevPlan customerDevPlan=selectByPrimaryKey(id);
        AssertUtil.isTrue(id==null||customerDevPlan==null,"待删除记录不存在!!!");
        customerDevPlan.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerDevPlan)<1,"删除失败!!!");
    }

}
