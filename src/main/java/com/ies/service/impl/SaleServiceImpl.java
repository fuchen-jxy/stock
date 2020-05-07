package com.ies.service.impl;

import com.ies.mapper.SaleMapper;
import com.ies.service.ISaleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-07 02:01
 **/
@Service
public class SaleServiceImpl implements ISaleService {

    @Resource
    private SaleMapper saleMapper;

}
