package com.emt.med.inventoryOrder;

import com.emt.med.location.Location;
import com.emt.med.order.OrderEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryOrderEntity extends OrderEntity {

    private InventoryOrderOperation operation;
    private String note;
    @ManyToOne
    @JsonManagedReference("inventoryOrder-destination")
    private Location destination;
    @ManyToOne
    @JsonManagedReference("inventoryOrder-source")
    private Location source;

}
