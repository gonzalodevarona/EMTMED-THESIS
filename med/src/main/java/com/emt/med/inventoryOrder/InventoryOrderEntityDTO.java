package com.emt.med.inventoryOrder;

import com.emt.med.order.OrderEntityDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryOrderEntityDTO extends OrderEntityDTO {
    @NotNull(message = "inventory order type shouldn't be null")
    private InventoryOrderType type;
    private String note;
}
