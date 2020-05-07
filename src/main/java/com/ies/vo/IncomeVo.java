package com.ies.vo;

import com.ies.domain.Income;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-06 21:49
 **/
public class IncomeVo extends Income {

    /**
     * 分页参数
     */
    private Integer page;
    private Integer limit;
    private Long [] ids;

    private String start;
    private String end;

    private String userName;

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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
