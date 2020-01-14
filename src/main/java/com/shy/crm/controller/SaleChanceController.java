package com.shy.crm.controller;

import com.shy.base.BaseController;
import com.shy.crm.model.ResultInfo;
import com.shy.crm.query.SaleChanceQuery;
import com.shy.crm.service.SaleChanceService;
import com.shy.crm.service.UserService;
import com.shy.crm.utils.LoginUserUtil;
import com.shy.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author suhongyv
 */
@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {
    @Resource
    private SaleChanceService saleChanceService;
    @Resource
    private UserService userService;

    @RequestMapping("index")
    public String index() {
        return "sale_chance";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery) {
        return saleChanceService.querySaleChanceByParams(saleChanceQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveSaleChance(HttpServletRequest request, SaleChance saleChance) {
        //设置创建人名
        saleChance.setCreateMan(userService.selectByPrimaryKey(LoginUserUtil.releaseUserIdFromCookie(request)).getTrueName());
        //调用service层,添加数据
        saleChanceService.saveSaleChance(saleChance);
        //返回成功数据
        return success("添加成功");
    }

    /**
     * 更新营销机会记录
     * @param saleChance
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance( SaleChance saleChance) {
        //调用service层,更新数据
        saleChanceService.updateSaleChance(saleChance);
        //返回成功数据
        return success("更新成功");
    }

    /**
     * 删除营销机会记录
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance( Integer[]ids) {
        //调用service层,删除数据
        saleChanceService.deleteSaleChance(ids);
        //返回成功数据
        return success("删除成功");
    }

    /**
     * 更新开发结果
     * @param saleChance
     * @return
     */
    @RequestMapping("updateDevResult")
    @ResponseBody
    public ResultInfo updateDevResult(SaleChance saleChance){
        saleChanceService.updateDevResult(saleChance);
        return success("更新成功!!!");
    }
}
