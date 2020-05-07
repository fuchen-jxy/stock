package com.ies.controller;

import com.ies.domain.Breakage;
import com.ies.domain.Item;
import com.ies.domain.User;
import com.ies.service.IBreakageService;
import com.ies.service.ItemService;
import com.ies.service.UserService;
import com.ies.utils.DataGridView;
import com.ies.utils.ResultObj;
import com.ies.vo.BreakageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-07 00:45
 **/
@RestController
@RequestMapping("breakage")
public class BreakageController {

    @Resource
    private IBreakageService breakageService;
    @Resource
    private UserService userService;
    @Resource
    private ItemService itemService;

    @RequestMapping("loadAllBreakage")
    public DataGridView loadAllBreakage(BreakageVo breakageVo) {
        DataGridView dataGridView = breakageService.loadAllBreakage(breakageVo);
        List<Breakage> breakageList = (List<Breakage>) dataGridView.getData();
        if (CollectionUtils.isEmpty(breakageList)) {
            return dataGridView;
        } else {
            List<BreakageVo> breakageVos = new ArrayList<>();
            for (Breakage breakage : breakageList) {
                BreakageVo _breakageVo = new BreakageVo();
                BeanUtils.copyProperties(breakage, _breakageVo);
                Item item = itemService.getItem(breakage.getItemId());
                _breakageVo.setItemName(item.getName());
                User user = userService.queryById(breakage.getUserId());
                _breakageVo.setUserName(user.getUsername());
                breakageVos.add(_breakageVo);
            }
            dataGridView.setData(breakageVos);
        }
        return dataGridView;
    }

    @RequestMapping("addBreakage")
    public ResultObj addBreakage(BreakageVo breakageVo) {
        breakageService.addBreakage(breakageVo);
        return ResultObj.ADD_SUCCESS;
    }

    @RequestMapping("updateBreakage")
    public ResultObj updateBreakage(BreakageVo breakageVo) {
        breakageService.updateBreakage(breakageVo);
        return ResultObj.UPDATE_SUCCESS;
    }

    @RequestMapping("deleteBatchBreakage")
    public ResultObj deleteBatchBreakage(BreakageVo breakageVo) {
        breakageService.deleteBatchBreakage(breakageVo.getIds());
        return ResultObj.DELETE_SUCCESS;
    }

    @RequestMapping("deleteBreakage")
    public ResultObj deleteBreakage(BreakageVo breakageVo) {
        breakageService.deleteBreakage(breakageVo.getId());
        return ResultObj.DELETE_SUCCESS;
    }

}
