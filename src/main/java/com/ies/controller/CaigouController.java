package com.ies.controller;

import com.ies.domain.Caigou;
import com.ies.domain.Item;
import com.ies.domain.User;
import com.ies.service.ICaigouService;
import com.ies.service.ItemService;
import com.ies.service.UserService;
import com.ies.utils.DataGridView;
import com.ies.utils.ResultObj;
import com.ies.vo.CaigouVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.testng.collections.Lists;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-06 00:20
 **/
@RestController
@RequestMapping("caigou")
public class CaigouController {

    @Resource
    private ICaigouService caigouService;
    @Resource
    private UserService userService;
    @Resource
    private ItemService itemService;

    @RequestMapping("loadAllCaigou")
    public DataGridView loadAllCaigou(CaigouVo caigouVo) {
        DataGridView dataGridView = caigouService.loadAllCaigou(caigouVo);
        List<Caigou> caigouList = (List<Caigou>) dataGridView.getData();
        if (CollectionUtils.isEmpty(caigouList)) {
            return dataGridView;
        } else {
            List<CaigouVo> caigouVoList = Lists.newArrayList();
            for (Caigou caigou : caigouList) {
                CaigouVo _caigouVo = new CaigouVo();
                BeanUtils.copyProperties(caigou, _caigouVo);
                User user = userService.queryById(caigou.getUserId());
                _caigouVo.setUserName(user.getUsername());
                Item item = itemService.getItem(caigou.getItemId());
                _caigouVo.setItemName(item.getName());
                caigouVoList.add(_caigouVo);
            }
            dataGridView.setData(caigouVoList);
        }
        return dataGridView;
    }

    @RequestMapping("addCaigou")
    public ResultObj addCaigou(CaigouVo caigouVo) {
        caigouService.addCaigou(caigouVo);
        return ResultObj.ADD_SUCCESS;
    }

    @RequestMapping("deleteBatchCaigou")
    public ResultObj deleteBatchCaigou(CaigouVo caigouVo) {
        caigouService.deleteBatchCaigou(caigouVo.getIds());
        return ResultObj.DELETE_SUCCESS;
    }

    @RequestMapping("deleteCaigou")
    public ResultObj deleteCaigou(CaigouVo caigouVo) {
        caigouService.deleteCaigou(caigouVo.getId());
        return ResultObj.DELETE_SUCCESS;
    }

}
