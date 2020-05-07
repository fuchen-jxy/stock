package com.ies.service.impl;



import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ies.domain.Menu;
import com.ies.domain.MenuCriteria;
import com.ies.mapper.MenuMapper;
import com.ies.service.MenuService;
import com.ies.utils.DataGridView;
import com.ies.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;


    @Override
    public List<Menu> queryAllMenuForList(MenuVo menuVo) {
        MenuCriteria example = new MenuCriteria();
        MenuCriteria.Criteria criteria = example.createCriteria();
        if(menuVo.getAvailable()!=null){
            criteria.andAvailableEqualTo(menuVo.getAvailable());
        }
        List<Menu> menus = menuMapper.selectByExample(example);
        return menus;
    };

    @Override
    public List<Menu> queryMenuByIdForList(Integer type) {
        MenuCriteria example = new MenuCriteria();
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(5);
        list.add(6);
        example.createCriteria().andIdIn(list);
        List<Menu> menus = menuMapper.selectByExample(example);
        return menus;
    }

    /**
     * 实现 A and (B OR C)
     * @param menuVo
     * @return
     */

    @Override
    public DataGridView queryAllMenu(MenuVo menuVo) {
        MenuCriteria example = new MenuCriteria();
        MenuCriteria.Criteria criteria = example.createCriteria();
        if(menuVo.getTitle()!="" && menuVo.getTitle()!=null){
            criteria.andTitleLike("%"+menuVo.getTitle()+"%");
        }
        if(menuVo.getId()!=null){
            criteria.andIdEqualTo(menuVo.getId());
        }
        MenuCriteria.Criteria criteria1 = example.createCriteria();
        if(menuVo.getTitle()!="" && menuVo.getTitle()!=null){
            criteria1.andTitleLike("%"+menuVo.getTitle()+"%");
        }
        if(menuVo.getId()!=null){
            criteria1.andPidEqualTo(menuVo.getId());
        }
        example.or(criteria1);
        Page<Object> page = PageHelper.startPage(menuVo.getPage(),menuVo.getLimit());
        List<Menu> menus = menuMapper.selectByExample (example);
        return new DataGridView(page.getTotal(),menus);
    }

    @Override
    public void addMenu(MenuVo menuVo) {
        if(menuVo.getId()!=null){
            menuVo.setId(null);
        }
        menuMapper.insert(menuVo);
    }

    @Override
    public void updateMenu(MenuVo menuVo) {
        menuMapper.updateByPrimaryKey(menuVo);
    }

    @Override
    public void deleteMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer checkMenuHasChildren(Integer id) {
        MenuCriteria example = new MenuCriteria();
        MenuCriteria.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(id);
        List<Menu> menus = menuMapper.selectByExample(example);
        return menus.size();
    }

}
