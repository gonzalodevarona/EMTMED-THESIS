package com.emt.med.location;

import com.emt.med.baseBatch.BaseBatch;
import com.emt.med.inventoryOrder.InventoryOrderEntity;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.supply.Supply;
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

    @OneToMany(mappedBy = "destination", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("inventoryOrder-destination")
    private List<InventoryOrderEntity> destinationList;

    @OneToMany(mappedBy = "source", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference("inventoryOrder-source")
    private List<InventoryOrderEntity> sourceList;
}
