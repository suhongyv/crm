package com.shy.crm.controller;

import com.shy.base.BaseController;
import com.shy.crm.query.CustomerDevPlanQuery;
import com.shy.crm.service.CustomerDevPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author suhongyv
 */
@Controller
@RequestMapping("cus_dev_plan")
public class CustomerDevPlanController extends BaseController {
    @Resource
    private CustomerDevPlanService customerDevPlanService;
    @RequestMapping("index")
    public String index(){
        return "cus_dev_plan";
    }
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCusDevPlanByParams(CustomerDevPlanQuery customerDevPlanQuery){
        return customerDevPlanService.queryByParamsForDataGrid(customerDevPlanQuery);
    }
}
