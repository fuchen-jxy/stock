package com.ies.controller;

import com.ies.service.ISaleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-07 02:00
 **/
@RestController
@RequestMapping("sale")
public class SaleController {

    @Resource
    private ISaleService saleService;



}
