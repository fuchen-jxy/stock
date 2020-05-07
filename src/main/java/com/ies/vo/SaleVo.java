package com.ies.vo;

import com.ies.domain.Sale;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-07 02:02
 **/
public class SaleVo extends Sale {

    /**
     * 分页参数
     */
    private Integer page;
    private Integer limit;
    private Long [] ids;

    private String itemName;
    private String userName;
    private String customerName;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
