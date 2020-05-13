package com.ies.service.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ies.domain.Income;
import com.ies.domain.IncomeCriteria;
import com.ies.domain.User;
import com.ies.mapper.IncomeMapper;
import com.ies.service.InComeService;
import com.ies.utils.DataGridView;
import com.ies.utils.WebUtils;
import com.ies.vo.IncomeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-06 21:47
 **/
@Service
public class InComeServiceImpl implements InComeService {

    @Resource
    private IncomeMapper incomeMapper;

    @Override
    public DataGridView loadAllIncome(IncomeVo incomeVo) {
        IncomeCriteria incomeCriteria = new IncomeCriteria();
        IncomeCriteria.Criteria criteria = incomeCriteria.createCriteria();
        if (!StringUtils.isEmpty(incomeVo.getStart())) {
            criteria.andCreatedGreaterThan(DateUtil.parse(incomeVo.getStart()));
        }
        if (!StringUtils.isEmpty(incomeVo.getEnd())) {
            criteria.andCreatedLessThanOrEqualTo(DateUtil.parse(incomeVo.getEnd()));
        }
        if (incomeVo.getType() != null) {
            criteria.andTypeEqualTo(incomeVo.getType());
        }
        Page<Object> page = PageHelper.startPage(incomeVo.getPage(), incomeVo.getLimit());
        List<Income> incomes = incomeMapper.selectByExample(incomeCriteria);
        return new DataGridView(page.getTotal(), incomes);
    }

    @Override
    public Integer addIncome(IncomeVo incomeVo) {
        User user = (User) WebUtils.getHttpSession().getAttribute("user");
        incomeVo.setUserId(user.getId());
        return incomeMapper.insert(incomeVo);
    }

    @Override
    public void updateIncome(IncomeVo incomeVo) {
        Income income = incomeMapper.selectByPrimaryKey(incomeVo.getId());
        if (incomeVo.getType() != null) {
            income.setType(incomeVo.getType());
        }
        if (incomeVo.getMoneyIn() != null) {
            income.setMoneyIn(incomeVo.getMoneyIn());
        }
        if (incomeVo.getMoneyOut() != null) {
            income.setMoneyOut(incomeVo.getMoneyOut());
        }
        if (!StringUtils.isEmpty(incomeVo.getSource())) {
            income.setSource(incomeVo.getSource());
        }
        if (!StringUtils.isEmpty(incomeVo.getRemark())) {
            income.setRemark(incomeVo.getRemark());
        }
        incomeMapper.updateByPrimaryKey(income);
    }

    @Override
    @Transactional
    public void deleteBatchIncome(Long[] ids) {
        for (Long id : ids) {
            deleteIncome(id);
        }
    }

    @Override
    public void deleteIncome(Long id) {
        checkDeleteCustomer(id);
        incomeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Map<String, Object> incomeTotalIncome(IncomeVo incomeVo) {
        BigDecimal inPrice = incomeMapper.incomeInPrice(incomeVo);
        BigDecimal outPrice = incomeMapper.incomeOutPrice(incomeVo);
        Map<String, Object> map = new HashMap<>();
        map.put("inPrice", inPrice);
        map.put("outPrice", outPrice);
        return map;
    }

    private void checkDeleteCustomer(Long id) {
        Income income = incomeMapper.selectByPrimaryKey(id);
        if (income.getRelId() != null) {
            throw new IllegalArgumentException("该财务单关联业务单，无法删除");
        }
    }
}
