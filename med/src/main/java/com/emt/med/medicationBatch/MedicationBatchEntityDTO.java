package com.emt.med.medicationBatch;

import com.emt.med.baseBatch.BaseBatchDTO;
import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.medicine.MedicineEntityDTO;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationBatchEntityDTO extends BaseBatchDTO {

    @Min(0)
    private Integer quantity;

    private String cum;

    private MedicineEntityDTO medicine;
}
