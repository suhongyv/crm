package com.shy.crm.service;

import com.shy.base.BaseService;
import com.shy.crm.dao.CustomerDevPlanMapper;
import com.shy.crm.dao.CustomerMapper;
import com.shy.crm.utils.AssertUtil;
import com.shy.crm.utils.PhoneUtil;
import com.shy.crm.vo.Customer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author suhongyv
 */
@Service
@SuppressWarnings("all")
public class CustomerService extends BaseService<Customer,Integer> {
    @Resource
    private CustomerMapper customerMapper;
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCustomer(Customer customer){
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());
        AssertUtil.isTrue(null!=customerMapper.queryCustomerByCusName(customer.getName()),"客户记录已存在");
        customer.setIsValid(1);
        customer.setState(0);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        String khno="KH_"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        customer.setKhno(khno);
        AssertUtil.isTrue(insertSelective(customer)<1,"添加失败");
    }

    private void checkParams(String name, String phone, String fr) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"客户名不能为空");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)),"请输入正确的手机号");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"法人不能为空");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCustomer(Customer customer){
        AssertUtil.isTrue(customer.getId()==null||selectByPrimaryKey(customer.getId())==null,"待更新客户记录不存在");
        checkParams(customer.getName(),customer.getPhone(),customer.getFr());
        Customer temp=selectByPrimaryKey(customer.getId());
        AssertUtil.isTrue(temp!=null&&!(customer.getId().equals(temp.getId())),"客户记录已存在");
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customer)<1,"客户记录更新失败");
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCustomer(Integer id){
        Customer temp=customerMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(id==null||temp==null,"待删除记录不存在");
        temp.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(temp)<1,"删除失败");
    }
}
