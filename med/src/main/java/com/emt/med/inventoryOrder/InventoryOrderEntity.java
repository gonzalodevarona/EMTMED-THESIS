package com.emt.med.inventoryOrder;

import com.emt.med.order.OrderEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryOrderEntity extends OrderEntity {

    private InventoryOrderType type;
    private String note;

}
