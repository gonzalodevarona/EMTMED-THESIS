package com.emt.med.pharmacy;

import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PharmacyEntityServiceImpl implements PharmacyEntityService{
    
    private PharmacyEntityRepository pharmacyEntityRepository;

    static PharmacyEntityMapper pharmacyEntityMapper = Mappers.getMapper(PharmacyEntityMapper.class);

    public PharmacyEntityServiceImpl(PharmacyEntityRepository pharmacyEntityRepository) {
        this.pharmacyEntityRepository = pharmacyEntityRepository;
    }

    @Override
    public PharmacyEntity getPharmacyEntityById(Long pharmacyEntityId) {
        return pharmacyEntityRepository.findById(pharmacyEntityId).orElseThrow(() -> new RuntimeException("No pharmacy found with id "+pharmacyEntityId));
    }

    @Override
    public PharmacyEntity getPharmacyByCategory(PharmacyCategory pharmacyCategory){
        return pharmacyEntityRepository.findByCategory(pharmacyCategory);
    }

    @Override
    public PharmacyEntityDTO getPharmacyEntityDTOById(Long pharmacyEntityId) {
        PharmacyEntity consumableEntity = pharmacyEntityRepository.findById(pharmacyEntityId).orElseThrow(() -> new RuntimeException("No pharmacy found with id "+pharmacyEntityId));
        return pharmacyEntityMapper.toDTO(consumableEntity);
    }

    @Override
    public List<PharmacyEntityDTO> getAllPharmacies() {
        return pharmacyEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream().map(pharmacyEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public PharmacyEntityDTO addPharmacy(PharmacyEntityDTO pharmacyEntityDTO) {
        if (pharmacyEntityDTO.getId() != null) {
            throw new RuntimeException("A new pharmacy cannot already have an ID");
        }
        if(pharmacyEntityDTO.getCategory() == PharmacyCategory.PRINCIPAL && getPharmacyByCategory(PharmacyCategory.PRINCIPAL) != null && getPharmacyByCategory(PharmacyCategory.WAREHOUSE) != null){
            throw new RuntimeException("Error: there is already a pharmacy with PRINCIPAL category");
        } else{
            PharmacyEntity consumableEntity = pharmacyEntityMapper.toEntity(pharmacyEntityDTO);
            consumableEntity = pharmacyEntityRepository.save(consumableEntity);
            return pharmacyEntityMapper.toDTO(consumableEntity);

        }

    }

    @Override
    @Transactional
    public PharmacyEntityDTO updatePharmacy(PharmacyEntityDTO pharmacyEntityDTO) {
        PharmacyEntity existingFieldEntity = pharmacyEntityRepository.findById(pharmacyEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No pharmacy found with id "+pharmacyEntityDTO.getId()));

        PharmacyEntity principalPharmacy = getPharmacyByCategory(PharmacyCategory.PRINCIPAL);
        PharmacyEntity warehousePharmacy = getPharmacyByCategory(PharmacyCategory.WAREHOUSE);
        if(existingFieldEntity.getCategory() != pharmacyEntityDTO.getCategory() &&
                principalPharmacy.getId() == existingFieldEntity.getId() &&
                warehousePharmacy.getId() == existingFieldEntity.getId()){
            throw new RuntimeException("Error: there is already a pharmacy with PRINCIPAL or WAREHOUSE category");

        } else if(existingFieldEntity.getCategory() == PharmacyCategory.SECONDARY &&
                pharmacyEntityDTO.getCategory() == PharmacyCategory.PRINCIPAL &&
                pharmacyEntityDTO.getCategory() == PharmacyCategory.WAREHOUSE &&
                principalPharmacy != null){
            throw new RuntimeException("Error: there is already a pharmacy with PRINCIPAL or WAREHOUSE category");

        } else{
            pharmacyEntityMapper.updatePharmacyFromDTO(pharmacyEntityDTO, existingFieldEntity);
            return pharmacyEntityMapper.toDTO(pharmacyEntityRepository.save(existingFieldEntity));
        }
    }

    @Override
    @Transactional
    public void deletePharmacy(Long id) {
        PharmacyEntity existingPharmacy = getPharmacyEntityById(id);

        if (existingPharmacy.getCategory() == PharmacyCategory.PRINCIPAL || existingPharmacy.getCategory() == PharmacyCategory.WAREHOUSE) {
            throw new RuntimeException("Error: a pharmacy with PRINCIPAL or WAREHOUSE category cannot be deleted");
        } else {
            pharmacyEntityRepository.delete(existingPharmacy);
        }
    }
}
