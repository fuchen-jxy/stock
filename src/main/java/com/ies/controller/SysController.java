package com.ies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转控制器
 */
@Controller
@RequestMapping("sys")
public class SysController {

    /**
     * 跳转到菜单管理
     * @return
     */
    @RequestMapping("toMenuManager")
    public String toMenuManager(){
        return "system/menu/menuManager";
    }

    /**
     * 跳转到菜单管理左边的菜单树页面
     * @return
     */
    @RequestMapping("toMenuLeft")
    public String toMenuLeft(){
        return "system/menu/menuLeft";
    }

    /**
     * 跳转到菜单管理右边的菜单列表
     * @return
     */
    @RequestMapping("toMenuRight")
    public String toMenuRight(){
        return "system/menu/menuRight";
    }

    /**
     * 跳转到用户管理
     * @return
     */
    @RequestMapping("toUserManager")
    public String toUserManager(){
        return "system/user/userManager";
    }

    /**
     * 跳转到供应商管理
     * @return
     */
    @RequestMapping("toSupplierManager")
    public String toSupplierManager() {
        return "system/supplier/supplierManager";
    }

    /**
     * 跳转到客户管理
     * @return
     */
    @RequestMapping("toCustomerManager")
    public String toCustomerManager() {
        return "system/customer/customerManager";
    }

    /**
     * 跳转到商品信息管理
     * @return
     */
    @RequestMapping("toItemManager")
    public String toItemManager() {
        return "system/item/itemManager";
    }

    /**
     * 收入支出管理
     * @return
     */
    @RequestMapping("toIncomeManager")
    public String toIncomeManager() {
        return "system/income/incomeManager";
    }

    /**
     * 采购采退管理
     * @return
     */
    @RequestMapping("toCaigouManager")
    public String toCaigouManager() {
        return "system/caigou/caigouManager";
    }

    /**
     * 商品报损
     * @return
     */
    @RequestMapping("toBreakageManager")
    public String toBreakageManager() {
        return "system/item/breakageManager";
    }

    /**
     * 销售管理
     * @return
     */
    @RequestMapping("toSaleManager")
    public String toSaleManager() {
        return "system/sale/saleManager";
    }

}
