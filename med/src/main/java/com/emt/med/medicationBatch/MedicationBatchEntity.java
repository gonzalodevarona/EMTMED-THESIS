package com.emt.med.medicationBatch;

import com.emt.med.baseBatch.BaseBatch;
import com.emt.med.batch.BatchEntity;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.supply.Supply;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationBatchEntity extends BaseBatch {

    private Integer quantity;
    private String cum;
    @ManyToOne
    @JsonBackReference("medicine-medicationBatch")
    private MedicineEntity medicine;
}
