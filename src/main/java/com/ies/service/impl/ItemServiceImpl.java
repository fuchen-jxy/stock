package com.ies.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ies.domain.Item;
import com.ies.domain.ItemCriteria;
import com.ies.mapper.ItemMapper;
import com.ies.service.ItemService;
import com.ies.utils.DataGridView;
import com.ies.vo.ItemVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: stock
 * @description:
 * @author: fuchen
 * @create: 2020-05-05 23:02
 **/
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemMapper itemMapper;

    @Override
    public DataGridView loadAllItem(ItemVo itemVo) {
        ItemCriteria itemCriteria = new ItemCriteria();
        ItemCriteria.Criteria criteria = itemCriteria.createCriteria();
        if (!StringUtils.isEmpty(itemVo.getName())) {
            criteria.andNameLike("%" + itemVo.getName() + "%");
        }
        if (!StringUtils.isEmpty(itemVo.getCode())) {
            criteria.andCodeLike("%" + itemVo.getCode() + "%");
        }
        Page<Object> page = PageHelper.startPage(itemVo.getPage(), itemVo.getLimit());
        List<Item> itemList = itemMapper.selectByExample(itemCriteria);
        return new DataGridView(page.getTotal(), itemList);
    }

    @Override
    public Integer addItem(ItemVo itemVo) {
        if (checkTheSameName(itemVo.getCode())) {
            throw new IllegalArgumentException("商品名已经存在");
        }
        return itemMapper.insert(itemVo);
    }

    @Override
    public void updateItem(ItemVo itemVo) {
        itemMapper.updateByPrimaryKey(itemVo);
    }

    @Override
    public void deleteItem(Long id) {
        checkDeleteItem(id);
        itemMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void deleteBatchItem(Long[] ids) {
        for (Long id : ids) {
            deleteItem(id);
        }
    }

    @Override
    public Item getItem(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public Object loadAllItemJson() {
        ItemCriteria itemCriteria = new ItemCriteria();
        itemCriteria.createCriteria();
        return itemMapper.selectByExample(itemCriteria);
    }

    private boolean checkTheSameName(String name) {
        ItemCriteria itemCriteria = new ItemCriteria();
        itemCriteria.createCriteria().andCodeEqualTo(name);
        List<Item> itemList = itemMapper.selectByExample(itemCriteria);
        return !CollectionUtils.isEmpty(itemList);
    }

    private void checkDeleteItem(Long id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        if (item == null) {
            throw new IllegalArgumentException("该商品不存在");
        }
        if (item.getStock() > 0) {
            throw new IllegalArgumentException("该商品尚有库存无法删除");
        }
    }

}
