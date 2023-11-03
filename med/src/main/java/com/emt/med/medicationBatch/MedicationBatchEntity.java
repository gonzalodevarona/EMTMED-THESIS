package com.emt.med.medicationBatch;

import com.emt.med.batch.BatchEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationBatchEntity extends BatchEntity {

    private Integer quantity;
    private String cum;
}
