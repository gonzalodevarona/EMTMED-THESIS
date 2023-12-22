package com.emt.med.inventoryOrder;

import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.medicine.MedicineEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryOrderEntityService {

    InventoryOrderEntityDTO getInventoryOrderEntityById(Long inventoryOrderEntityId);
    List<InventoryOrderEntityDTO> getAllInventoryOrders();
    InventoryOrderEntityDTO addInventoryOrder(InventoryOrderEntityDTO inventoryOrderEntityDTO);
    InventoryOrderEntityDTO processNewMedicationBatches(MedicineEntity medicine);
    InventoryOrderEntityDTO processNewBatches(ConsumableEntity consumable);
    InventoryOrderEntityDTO updateInventoryOrder(InventoryOrderEntityDTO inventoryOrderEntityDTO);

    void deleteInventoryOrder(Long id);
}
