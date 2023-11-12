package com.emt.med.medicine;

import com.emt.med.medicationBatch.MedicationBatchEntityDTO;
import com.emt.med.supply.SupplyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineEntityDTO extends SupplyDTO {
    private String activePharmaceuticalIngredient;
    private String concentration;
    private List<MedicationBatchEntityDTO> batches;
}
