package com.emt.med.medicationBatch;

import com.emt.med.batch.BatchEntity;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class MedicationBatchEntityServiceImpl implements MedicationBatchEntityService{

    private MedicationBatchEntityRepository medicationBatchEntityRepository;

    static MedicationBatchEntityMapper medicationBatchEntityMapper = Mappers.getMapper(MedicationBatchEntityMapper.class);

    public MedicationBatchEntityServiceImpl(MedicationBatchEntityRepository medicationBatchEntityRepository) {
        this.medicationBatchEntityRepository = medicationBatchEntityRepository;
    }

    @Override
    public MedicationBatchEntity getMedicationBatchEntityById(Long medicationBatchEntityId) {
        return medicationBatchEntityRepository.findById(medicationBatchEntityId).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchEntityId));

    }

    @Override
    public MedicationBatchEntityDTO getMedicationBatchEntityDTOById(Long medicationBatchEntityId) {
        MedicationBatchEntity medicationBatchEntity = medicationBatchEntityRepository.findById(medicationBatchEntityId).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchEntityId));
        return medicationBatchEntityMapper.toDTO(medicationBatchEntity);
    }

    @Override
    public List<MedicationBatchEntityDTO> getAllMedicationBatches() {
        return medicationBatchEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "expirationDate")).stream().map(medicationBatchEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public MedicationBatchEntity saveMedicationBatch(MedicationBatchEntity medicationBatchEntity) {
        return medicationBatchEntityRepository.save(medicationBatchEntity);
    }

    @Override
    @Transactional
    public MedicationBatchEntityDTO addMedicationBatch(MedicationBatchEntityDTO medicationBatchEntityDTO) {
        if (medicationBatchEntityDTO.getId() != null) {
            throw new RuntimeException("A new medication batch cannot already have an ID");
        }
        MedicationBatchEntity medicationBatchEntity = medicationBatchEntityMapper.toEntity(medicationBatchEntityDTO);
        medicationBatchEntity = medicationBatchEntityRepository.save(medicationBatchEntity);
        return medicationBatchEntityMapper.toDTO(medicationBatchEntity);
    }

    @Override
    @Transactional
    public MedicationBatchEntityDTO updateMedicationBatch(MedicationBatchEntityDTO medicationBatchEntityDTO) {
        MedicationBatchEntity existingMedicationBatchEntity = medicationBatchEntityRepository.findById(medicationBatchEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchEntityDTO.getId()));
        medicationBatchEntityMapper.updateMedicationBatchFromDto(medicationBatchEntityDTO, existingMedicationBatchEntity);
        return medicationBatchEntityMapper.toDTO(medicationBatchEntityRepository.save(existingMedicationBatchEntity));
    }


    @Override
    @Transactional
    public void deleteMedicationBatch(Long id) {
        medicationBatchEntityRepository.deleteById(id);
    }
}
