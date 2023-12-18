package com.emt.med.medicationBatch;

import com.emt.med.baseBatch.BaseBatch;
import com.emt.med.inventoryOrder.InventoryOrderEntity;
import com.emt.med.location.Location;
import com.emt.med.medicine.MedicineEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationBatchEntity extends BaseBatch {

    private String cum;
    @ManyToOne
    @JsonBackReference("medicine-medicationBatch")
    private MedicineEntity medicine;

    @ManyToOne
    @JsonBackReference("inventoryOrder-medicationBatch")
    private InventoryOrderEntity inventoryOrder;

    @ManyToOne
    @JsonBackReference("location-medicationBatch")
    private Location location;
}
