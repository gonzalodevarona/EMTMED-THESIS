package com.emt.med.medicationBatchRequest;

import com.emt.med.medicationBatch.MedicationBatchEntityDTO;
import com.emt.med.supplyOrder.SupplyOrderEntityDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationBatchRequestEntityDTO {

    @Positive(message = "id number should be greater than zero")
    private Long id;

    @NotNull(message = "supplyOrder should not be null")
    private SupplyOrderEntityDTO supplyOrder;

    @NotNull(message = "supplyOrder should not be null")
    private MedicationBatchEntityDTO medicationBatch;

    @Positive(message = "quantity should be greater than zero")
    private int quantity;
}
