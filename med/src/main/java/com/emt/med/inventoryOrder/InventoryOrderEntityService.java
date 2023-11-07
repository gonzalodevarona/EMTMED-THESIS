package com.emt.med.inventoryOrder;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryOrderEntityService {

    InventoryOrderEntityDTO getInventoryOrderEntityById(Long inventoryOrderEntityId);
    List<InventoryOrderEntityDTO> getAllInventoryOrders();
    InventoryOrderEntityDTO addInventoryOrder(InventoryOrderEntityDTO inventoryOrderEntityDTO);

    InventoryOrderEntityDTO updateInventoryOrder(InventoryOrderEntityDTO inventoryOrderEntityDTO);

    void deleteInventoryOrder(Long id);
}
