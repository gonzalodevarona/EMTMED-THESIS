package com.emt.med.medicine;

import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.consumable.ConsumableEntityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicineEntityService {
    MedicineEntity getMedicineEntityById(Long medicineEntityId);
    MedicineEntityDTO getMedicineEntityDTOById(Long medicineEntityId);

    List<MedicineEntityDTO> getAllMedicines();

    MedicineEntityDTO addMedicine(MedicineEntityDTO medicineEntityDTO);

    MedicineEntityDTO updateMedicine(MedicineEntityDTO medicineEntityDTO);

    void deleteMedicine(Long id);
}
