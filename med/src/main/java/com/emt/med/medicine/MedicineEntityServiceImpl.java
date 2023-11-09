package com.emt.med.medicine;

import com.emt.med.supply.Supply;
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
    private WeightUnitEntityService weightUnitEntityService;

    static MedicineEntityMapper medicineEntityMapper = Mappers.getMapper(MedicineEntityMapper.class);

    public MedicineEntityServiceImpl(MedicineEntityRepository medicineEntityRepository, WeightUnitEntityService weightUnitEntityService) {
        this.medicineEntityRepository = medicineEntityRepository;
        this.weightUnitEntityService = weightUnitEntityService;
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

        return medicineEntityMapper.toDTO(medicineEntity);
    }

    @Override
    @Transactional
    public MedicineEntity addWeightUnitToMedicine(WeightUnitEntity weightUnit, MedicineEntity medicine) {
        if (weightUnit == null){
            throw new RuntimeException("Error: In order to add a medicine, a weight unit must be added or assigned to it");
        } else if (weightUnit.getSupplyList() == null){
            weightUnit.setSupplyList(new ArrayList<Supply>());
        } else if (weightUnit.getId() == null){
            weightUnit = weightUnitEntityService.saveWeightUnitEntity(weightUnit);
        }

        medicine.setWeightUnit(weightUnit);
        weightUnit.getSupplyList().add(medicine);
        weightUnitEntityService.saveWeightUnitEntity(weightUnit);

        return saveMedicineEntity(medicine);
    }

    public MedicineEntity removeWeightUnitFromMedicine(MedicineEntity medicine) {
        medicine.getWeightUnit().getSupplyList().remove(medicine);
        medicine.setWeightUnit(null);
        weightUnitEntityService.saveWeightUnitEntity(medicine.getWeightUnit());
        return saveMedicineEntity(medicine);
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
