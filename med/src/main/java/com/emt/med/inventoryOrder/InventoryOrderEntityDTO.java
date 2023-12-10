package com.emt.med.inventoryOrder;

import com.emt.med.location.LocationDTO;
import com.emt.med.order.OrderEntityDTO;
import com.emt.med.supply.SupplyDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("inventoryOrder")
public class InventoryOrderEntityDTO extends OrderEntityDTO {
    @NotNull(message = "inventory order operation shouldn't be null")
    private InventoryOrderOperation operation;
    @NotNull(message = "destination shouldn't be null")
    private LocationDTO destination;
    @NotNull(message = "source shouldn't be null")
    private LocationDTO source;
    private List<SupplyDTO> supplies;
}
