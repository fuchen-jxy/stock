package com.ies.service;


import com.ies.domain.Menu;
import com.ies.utils.DataGridView;
import com.ies.vo.MenuVo;

import java.util.List;

public interface MenuService {
    /**
     * 查询所有菜单
     */
    public List<Menu> queryAllMenuForList(MenuVo menuVo);

    /**
     * 普通用户根据id查询
     */
    public List<Menu> queryMenuByIdForList(Integer type);

    /**
     * 分页查询
     */
    public DataGridView queryAllMenu(MenuVo menuVo);

    void addMenu(MenuVo menuVo);

    void updateMenu(MenuVo menuVo);

    void deleteMenu(Integer id);

    Integer checkMenuHasChildren(Integer id);
}

