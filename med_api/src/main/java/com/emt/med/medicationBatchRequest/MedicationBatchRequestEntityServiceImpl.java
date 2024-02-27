package com.emt.med.medicationBatchRequest;

import com.emt.med.medicationBatch.MedicationBatchEntityRepository;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicationBatchRequestEntityServiceImpl implements MedicationBatchRequestEntityService {

    private MedicationBatchRequestEntityRepository medicationBatchRequestEntityRepository;
    private MedicationBatchEntityRepository medicationBatchEntityRepository;

    static MedicationBatchRequestEntityMapper medicationBatchRequestEntityMapper = Mappers.getMapper(MedicationBatchRequestEntityMapper.class);

    public MedicationBatchRequestEntityServiceImpl(MedicationBatchRequestEntityRepository medicationBatchRequestEntityRepository, MedicationBatchEntityRepository medicationBatchEntityRepository) {
        this.medicationBatchRequestEntityRepository = medicationBatchRequestEntityRepository;
        this.medicationBatchEntityRepository = medicationBatchEntityRepository;
    }

    @Override
    public MedicationBatchRequestEntityDTO getMedicationBatchRequestDTOtById(Long medicationBatchRequestEntityId) {
        MedicationBatchRequestEntity medicationBatchRequestEntity = medicationBatchRequestEntityRepository.findById(medicationBatchRequestEntityId).orElseThrow(() -> new RuntimeException("No medication batch request found with id "+medicationBatchRequestEntityId));
        return medicationBatchRequestEntityMapper.toDTO(medicationBatchRequestEntity);
    }

    @Override
    public List<MedicationBatchRequestEntityDTO> getAllMedicationBatchRequests() {
        return medicationBatchRequestEntityRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream().map(medicationBatchRequestEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }


    @Override
    @Transactional
    public MedicationBatchRequestEntityDTO addMedicationBatchRequest(MedicationBatchRequestEntityDTO medicationBatchRequestEntityDTO) {
        if (medicationBatchRequestEntityDTO.getId() != null) {
            throw new RuntimeException("A medication batch request unit cannot already have an ID");
        }
        MedicationBatchRequestEntity medicationBatchRequestEntity = medicationBatchRequestEntityMapper.toEntity(medicationBatchRequestEntityDTO);

        return medicationBatchRequestEntityMapper.toDTO(saveMedicationBatchRequest(medicationBatchRequestEntity));
    }

    @Override
    @Transactional
    public MedicationBatchRequestEntity saveMedicationBatchRequest(MedicationBatchRequestEntity medicationBatchRequestEntity) {
        return medicationBatchRequestEntityRepository.save(medicationBatchRequestEntity);
    }
}
