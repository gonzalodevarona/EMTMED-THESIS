package com.emt.med.medicine;

import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.supply.Supply;
import com.emt.med.weightUnit.WeightUnitEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicineEntityService {
    MedicineEntity getMedicineEntityById(Long medicineEntityId);
    MedicineEntityDTO getMedicineEntityDTOById(Long medicineEntityId);

    List<MedicineEntityDTO> getAllMedicines();

    MedicineEntityDTO addMedicine(MedicineEntityDTO medicineEntityDTO);

    Supply addWeightUnitToMedicine(WeightUnitEntity weightUnit, MedicineEntity medicine);

    MedicineEntityDTO removeWeightUnitFromMedicine(Long medicineEntityId);

    Supply addCountingUnitToMedicine(CountingUnitEntity countingUnit, MedicineEntity medicine);

    MedicineEntityDTO removeCountingUnitFromMedicine(Long medicineEntityId);

    MedicineEntityDTO updateMedicine(MedicineEntityDTO medicineEntityDTO);

    void deleteMedicine(Long id);
}
