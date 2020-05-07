package com.ies.vo;

import com.ies.domain.Item;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-05 23:03
 **/
public class ItemVo extends Item {

    /**
     * 分页参数
     */
    private Integer page;
    private Integer limit;
    private Long [] ids;

    private String supplierName;

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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
