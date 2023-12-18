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

    @OneToMany(mappedBy = "inventoryOrder", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("inventoryOrder-medicationBatch")
    private List<MedicationBatchEntity> medicationBatches;

    @OneToMany(mappedBy = "inventoryOrder", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("inventoryOrder-batch")
    private List<BatchEntity> batches;

    @ManyToOne
    @JsonManagedReference("source-inventoryOrder")
    private Location source;

}
