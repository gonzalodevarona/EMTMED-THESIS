package com.emt.med.inventoryOrder;

import com.emt.med.batch.BatchEntity;
import com.emt.med.location.Location;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.order.OrderEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryOrderEntity extends OrderEntity {

    private InventoryOrderOperation operation;

    @ManyToOne
    @JsonManagedReference("destination-inventoryOrder")
    private Location destination;

    @ManyToMany(mappedBy = "inventoryOrders", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("inventoryOrder-medicationBatch")
    @ToString.Exclude
    private List<MedicationBatchEntity> medicationBatches;

    @ManyToMany(mappedBy = "inventoryOrders", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("inventoryOrder-batch")
    @ToString.Exclude
    private List<BatchEntity> batches;


}
