package com.emt.med.inventoryOrder;

import com.emt.med.location.Location;
import com.emt.med.order.OrderEntity;
import com.emt.med.supply.Supply;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryOrderEntity extends OrderEntity {

    private InventoryOrderOperation operation;
    @ManyToOne
    @JsonManagedReference("inventoryOrder-destination")
    private Location destination;
    @ManyToMany
    private Set<Supply> supplies;
    @ManyToOne
    @JsonManagedReference("inventoryOrder-source")
    private Location source;

}
