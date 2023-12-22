package com.emt.med.batch;

import com.emt.med.baseBatch.BaseBatchDTO;
import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.inventoryOrder.InventoryOrderEntityDTO;
import com.emt.med.location.LocationDTO;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchEntityDTO extends BaseBatchDTO {
    private ConsumableEntityDTO consumable;
    private LocationDTO location;
    private List<InventoryOrderEntityDTO> inventoryOrders;
}
