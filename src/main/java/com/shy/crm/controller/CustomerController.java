package com.shy.crm.controller;

import com.shy.base.BaseController;
import com.shy.crm.annotations.RequirePermission;
import com.shy.crm.model.ResultInfo;
import com.shy.crm.query.CustomerQuery;
import com.shy.crm.service.CustomerService;
import com.shy.crm.vo.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author suhongyv
 */
@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {
    @Resource
    private CustomerService customerService;
    @RequestMapping("index")
    public String index(){
        return "customer";
    }
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerByParams(CustomerQuery customerQuery){
        return  customerService.queryByParamsForDataGrid(customerQuery);
    }
    @ResponseBody
    @RequestMapping("save")
    public ResultInfo saveCustomer(Customer customer){
        customerService.saveCustomer(customer);
        return success();
    }
    @ResponseBody
    @RequestMapping("update")
    public ResultInfo updateCustomer(Customer customer){
        customerService.updateCustomer(customer);
        return success();
    }
    @ResponseBody
    @RequestMapping("delete")
    public ResultInfo deleteCustomer(Integer id){
        customerService.deleteCustomer(id);
        return success();
    }
}
