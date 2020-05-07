package com.ies.service;

import com.ies.domain.Item;
import com.ies.utils.DataGridView;
import com.ies.utils.ResultObj;
import com.ies.vo.ItemVo;

public interface ItemService {
    DataGridView loadAllItem(ItemVo itemVo);

    Integer addItem(ItemVo itemVo);

    void updateItem(ItemVo itemVo);

    void deleteItem(Long id);

    void deleteBatchItem(Long[] ids);

    Item getItem(Long itemId);

    Object loadAllItemJson();
}
