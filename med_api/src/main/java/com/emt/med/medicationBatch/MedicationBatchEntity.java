package com.emt.med.medicationBatch;

import com.emt.med.baseBatch.BaseBatch;
import com.emt.med.inventoryOrder.InventoryOrderEntity;
import com.emt.med.location.Location;
import com.emt.med.medicine.MedicineEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"medicine", "inventoryOrders"})
public class MedicationBatchEntity extends BaseBatch {

    private String cum;
    @ManyToOne
    @JsonBackReference("medicine-medicationBatch")
    private MedicineEntity medicine;

    @ManyToMany
    @JsonBackReference("inventoryOrder-medicationBatch")
    private List<InventoryOrderEntity> inventoryOrders;

    @ManyToOne
    @JsonBackReference("location-medicationBatch")
    private Location location;
}
