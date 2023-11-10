package com.emt.med.medicine;

import com.emt.med.countingUnit.CountingUnitEntity;
import com.emt.med.supply.Supply;
import com.emt.med.supply.SupplyMapper;
import com.emt.med.supply.SupplyService;
import com.emt.med.weightUnit.WeightUnitEntity;
import com.emt.med.weightUnit.WeightUnitEntityDTO;
import com.emt.med.weightUnit.WeightUnitEntityService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineEntityServiceImpl implements MedicineEntityService {
    
    private MedicineEntityRepository medicineEntityRepository;

    private SupplyService supplyService;

    static MedicineEntityMapper medicineEntityMapper = Mappers.getMapper(MedicineEntityMapper.class);

    public MedicineEntityServiceImpl(MedicineEntityRepository medicineEntityRepository, WeightUnitEntityService weightUnitEntityService, SupplyService supplyService) {
        this.medicineEntityRepository = medicineEntityRepository;
        this.supplyService = supplyService;
    }

    @Override
    public MedicineEntityDTO getMedicineEntityDTOById(Long medicineEntityId) {
        MedicineEntity consumableEntity = medicineEntityRepository.findById(medicineEntityId).orElseThrow(() -> new RuntimeException("No medicine found with id "+medicineEntityId));
        return medicineEntityMapper.toDTO(consumableEntity);
    }

    @Override
    public MedicineEntity getMedicineEntityById(Long medicineEntityId) {
        return medicineEntityRepository.findById(medicineEntityId).orElseThrow(() -> new RuntimeException("No medicine found with id "+medicineEntityId));
    }

    @Override
    public List<MedicineEntityDTO> getAllMedicines() {
        return medicineEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "activePharmaceuticalIngredient")).stream().map(medicineEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public MedicineEntityDTO addMedicine(MedicineEntityDTO medicineEntityDTO) {
        if (medicineEntityDTO.getId() != null) {
            throw new RuntimeException("A new medicine cannot already have an ID");
        }
        MedicineEntity medicineEntity = medicineEntityMapper.toEntity((MedicineEntityDTO) medicineEntityDTO);
        medicineEntity = medicineEntityRepository.save(medicineEntity);
        addWeightUnitToMedicine(medicineEntity.getWeightUnit(), medicineEntity);
        addCountingUnitToMedicine(medicineEntity.getCountingUnit(), medicineEntity);

        return medicineEntityMapper.toDTO(medicineEntity);
    }

    @Override
    @Transactional
    public Supply addWeightUnitToMedicine(WeightUnitEntity weightUnit, MedicineEntity medicine) {
        return saveMedicineEntity((MedicineEntity) supplyService.addWeightUnitToSupply(weightUnit, medicine));
    }

    @Override
    @Transactional
    public MedicineEntityDTO removeWeightUnitFromMedicine(Long medicineEntityId) {
        return medicineEntityMapper.toDTO(saveMedicineEntity((MedicineEntity) supplyService.removeWeightUnitFromSupply(getMedicineEntityById(medicineEntityId))));
    }

    @Override
    @Transactional
    public Supply addCountingUnitToMedicine(CountingUnitEntity countingUnit, MedicineEntity medicine) {
        return saveMedicineEntity((MedicineEntity) supplyService.addCountingUnitToSupply(countingUnit, medicine));
    }

    @Override
    @Transactional
    public MedicineEntityDTO removeCountingUnitFromMedicine(Long medicineEntityId) {
        return medicineEntityMapper.toDTO(saveMedicineEntity((MedicineEntity) supplyService.removeCountingUnitFromSupply(getMedicineEntityById(medicineEntityId))));
    }


    public MedicineEntity saveMedicineEntity(MedicineEntity medicineEntity) {
        return medicineEntityRepository.save(medicineEntity);
    }


    @Override
    @Transactional
    public MedicineEntityDTO updateMedicine(MedicineEntityDTO medicineEntityDTO) {
        MedicineEntity existingFieldEntity = medicineEntityRepository.findById(medicineEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No medicine found with id "+medicineEntityDTO.getId()));
        medicineEntityMapper.updateMedicineFromDTO((MedicineEntityDTO) medicineEntityDTO, existingFieldEntity);
        return medicineEntityMapper.toDTO(medicineEntityRepository.save(existingFieldEntity));
    }

    @Override
    @Transactional
    public void deleteMedicine(Long id) {
            medicineEntityRepository.deleteById(id);
    }

    

}
