package com.ies.vo;

import com.ies.domain.Stock;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-09 02:28
 **/
public class StockVo extends Stock {

    /**
     * 分页参数
     */
    private Integer page;
    private Integer limit;
    private Long [] ids;

    private Integer dayS;
    private Integer dayE;
    private String itemName;

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

    public Integer getDayS() {
        return dayS;
    }

    public void setDayS(Integer dayS) {
        this.dayS = dayS;
    }

    public Integer getDayE() {
        return dayE;
    }

    public void setDayE(Integer dayE) {
        this.dayE = dayE;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
