package com.ies.controller;


import com.ies.constast.SysConstast;
import com.ies.domain.Menu;
import com.ies.domain.User;
import com.ies.service.MenuService;
import com.ies.utils.*;
import com.ies.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单管理控制器
 */
@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 树型结构展示左侧
     * @param menuVo
     * @return
     */
    @RequestMapping("loadIndexleftMenuJson")
    public List<TreeNode> loadIndexLeftMenuJson(MenuVo menuVo){
        //得到当前登陆的用户对象
        User user =(User) WebUtils.getHttpSession().getAttribute("user");

        List<Menu> list;
        menuVo.setAvailable(SysConstast.AVAILABLE_TRUE);//只查询可用的
        if(user.getType().equals(SysConstast.USER_TYPE_SUPER)){
            list = menuService.queryAllMenuForList(menuVo);
        }else {
            list = menuService.queryMenuByIdForList(user.getType());
        }
        List<TreeNode> nodes = getNodes(list);
        return TreeNodeBuilder.builder(nodes,1);
    }

    @RequestMapping("loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson(MenuVo menuVo){
        menuVo.setAvailable(SysConstast.AVAILABLE_TRUE);
        List<Menu> menus = menuService.queryAllMenuForList(menuVo);
        List<TreeNode> nodes = getNodes(menus);
        return new DataGridView(nodes);

    }
    public List<TreeNode> getNodes(List<Menu> menus){
        List<TreeNode> nodes = new ArrayList<> ();
        for (Menu menu: menus) {
            Integer id = menu.getId();
            Integer pid = menu.getPid();
            String title = menu.getTitle();
            String icon = menu.getIcon();
            String href = menu.getHref();
            Boolean spread = menu.getSpread()== SysConstast.SPREAD_TRUE?true:false;
            String target = menu.getTarget();
            nodes.add(new TreeNode(id,pid,title,icon,href,spread,target));
        }
        return nodes;
    }
    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenu(MenuVo menuVo){
        return menuService.queryAllMenu(menuVo);
    }
    @RequestMapping("addMenu")
    public ResultObj addMenu(MenuVo menuVo){
        try{
            menuService.addMenu(menuVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    @RequestMapping("updateMenu")
    public ResultObj updateMenu(MenuVo menuVo){
        try{
            menuService.updateMenu(menuVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping("deleteMenu")
    public void deleteMenu(MenuVo menuVo){
        menuService.deleteMenu(menuVo.getId());
    }

    @RequestMapping("checkMenuHasChildren")
    public ResultObj checkMenuHasChildren (MenuVo menuVo){
        Integer count = menuService.checkMenuHasChildren(menuVo.getId());
        if(count>0){
            return ResultObj.STATUS_TRUE;
        }else{
            return ResultObj.STATUS_FALSE;
        }
    }

}
