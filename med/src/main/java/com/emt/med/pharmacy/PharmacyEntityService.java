package com.emt.med.pharmacy;

import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PharmacyEntityService {

    PharmacyEntity getPharmacyEntityById(Long pharmacyEntityId);
    PharmacyEntityDTO getPharmacyEntityDTOById(Long pharmacyEntityId);

    List<PharmacyEntityDTO> getAllPharmacies();

    PharmacyEntity getPharmacyByCategory(PharmacyCategory pharmacyCategory);
    PharmacyEntityDTO addPharmacy(PharmacyEntityDTO pharmacyEntityDTO);

    PharmacyEntityDTO updatePharmacy(PharmacyEntityDTO pharmacyEntityDTO);

    void deletePharmacy(Long id);
}
