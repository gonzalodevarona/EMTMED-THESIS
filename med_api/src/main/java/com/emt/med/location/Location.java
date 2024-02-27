package com.emt.med.location;

import com.emt.med.batch.BatchEntity;
import com.emt.med.inventoryOrder.InventoryOrderEntity;
import com.emt.med.medicationBatch.MedicationBatchEntity;
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
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "location", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("location-medicationBatch")
    private List<MedicationBatchEntity> medicationBatchList;


    @OneToMany(mappedBy = "location", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("location-batch")
    private List<BatchEntity> batchList;

    @OneToMany(mappedBy = "destination", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("destination-inventoryOrder")
    private List<InventoryOrderEntity> destinationList;

}
