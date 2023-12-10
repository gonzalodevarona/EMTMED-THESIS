package com.emt.med.medicationBatch;

import com.emt.med.baseBatch.BaseBatchDTO;
import com.emt.med.location.LocationDTO;
import com.emt.med.medicine.MedicineEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationBatchEntityDTO extends BaseBatchDTO {

    private String cum;

    private MedicineEntityDTO medicine;
    private LocationDTO location;
}
