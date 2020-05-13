package com.ies.controller;

import com.google.common.collect.Lists;
import com.ies.domain.Income;
import com.ies.domain.User;
import com.ies.service.InComeService;
import com.ies.service.UserService;
import com.ies.utils.DataGridView;
import com.ies.utils.ResultObj;
import com.ies.vo.IncomeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-06 21:46
 **/
@RestController
@RequestMapping("income")
public class InComeController {

    @Resource
    private InComeService inComeService;
    @Resource
    private UserService userService;

    @RequestMapping("loadAllIncome")
    public DataGridView loadAllIncome(IncomeVo incomeVo) {
        if (!StringUtils.isEmpty(incomeVo.getStart())) {
            incomeVo.setStart(incomeVo.getStart().replace(",", ""));
        }
        DataGridView dataGridView = inComeService.loadAllIncome(incomeVo);
        List<Income> incomeList = (List<Income>) dataGridView.getData();
        if (CollectionUtils.isEmpty(incomeList)) {
            return dataGridView;
        } else {
            List<IncomeVo> incomeVos = Lists.newArrayList();
                for (Income income : incomeList) {
                IncomeVo _income = new IncomeVo();
                BeanUtils.copyProperties(income, _income);
                User user = userService.queryById(income.getUserId());
                _income.setUserName(user.getUsername());
                incomeVos.add(_income);
            }
            dataGridView.setData(incomeVos);
        }
        return dataGridView;
    }

    @RequestMapping("incomeTotalIncome")
    public Map<String, Object> incomeTotalIncome(IncomeVo incomeVo) {
        return inComeService.incomeTotalIncome(incomeVo);
    }

    @RequestMapping("addIncome")
    public ResultObj addIncome(IncomeVo incomeVo) {
        inComeService.addIncome(incomeVo);
        return ResultObj.ADD_SUCCESS;
    }

    @RequestMapping("updateIncome")
    public ResultObj updateIncome(IncomeVo incomeVo) {
        inComeService.updateIncome(incomeVo);
        return ResultObj.UPDATE_SUCCESS;
    }

    @RequestMapping("deleteBatchIncome")
    public ResultObj deleteBatchIncome(IncomeVo incomeVo) {
        inComeService.deleteBatchIncome(incomeVo.getIds());
        return ResultObj.DELETE_SUCCESS;
    }

    @RequestMapping("deleteIncome")
    public ResultObj deleteIncome(IncomeVo incomeVo) {
        inComeService.deleteIncome(incomeVo.getId());
        return ResultObj.DELETE_SUCCESS;
    }

}
