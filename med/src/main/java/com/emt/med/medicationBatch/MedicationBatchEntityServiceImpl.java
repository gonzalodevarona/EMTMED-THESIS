package com.emt.med.medicationBatch;

import com.emt.med.batch.BatchEntity;
import com.emt.med.location.Location;
import com.emt.med.location.LocationMapper;
import com.emt.med.location.LocationRepository;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.medicine.MedicineEntityMapper;
import com.emt.med.medicine.MedicineEntityService;
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

    private LocationRepository locationRepository;

    static MedicationBatchEntityMapper medicationBatchEntityMapper = Mappers.getMapper(MedicationBatchEntityMapper.class);
    static MedicineEntityMapper medicineEntityMapper = Mappers.getMapper(MedicineEntityMapper.class);
    static LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    public MedicationBatchEntityServiceImpl(MedicationBatchEntityRepository medicationBatchEntityRepository, LocationRepository locationRepository) {
        this.medicationBatchEntityRepository = medicationBatchEntityRepository;
        this.locationRepository = locationRepository;
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


    public MedicineEntityDTO getMedicineByMedicationBatchId(Long medicationBatchEntityId){
        MedicationBatchEntity medicationBatchEntity = medicationBatchEntityRepository.findById(medicationBatchEntityId).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchEntityId));
        return medicineEntityMapper.toDTO(medicationBatchEntity.getMedicine());
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
    public MedicationBatchEntityDTO updateMedicationBatch(MedicationBatchEntity medicationBatchEntity) {

        MedicationBatchEntity existingMedicationBatchEntity = medicationBatchEntityRepository.findById(medicationBatchEntity.getId()).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchEntity.getId()));

        if(medicationBatchEntity.getQuantity() != null) {
            existingMedicationBatchEntity.setQuantity(medicationBatchEntity.getQuantity());
        }
        if(medicationBatchEntity.getCum() != null) {
            existingMedicationBatchEntity.setCum(medicationBatchEntity.getCum());
        }
        if(medicationBatchEntity.getMedicine() != null) {
            existingMedicationBatchEntity.setMedicine(medicationBatchEntity.getMedicine());
        }
//        if(medicationBatchEntity.getLocation() != null) {
//            existingMedicationBatchEntity.setLocation(medicationBatchEntity.getLocation());
//        }
        if(medicationBatchEntity.getManufacturer() != null) {
            existingMedicationBatchEntity.setManufacturer(medicationBatchEntity.getManufacturer());
        }
        if(medicationBatchEntity.getExpirationDate() != null) {
            existingMedicationBatchEntity.setExpirationDate(medicationBatchEntity.getExpirationDate());
        }
//        if(medicationBatchEntity.getAdministrationRoute() != null) {
//            existingMedicationBatchEntity.setAdministrationRoute(medicationBatchEntity.getAdministrationRoute());
//        }
        if(medicationBatchEntity.getStatus() != null) {
            existingMedicationBatchEntity.setStatus(medicationBatchEntity.getStatus());
        }

        return medicationBatchEntityMapper.toDTO(medicationBatchEntityRepository.save(existingMedicationBatchEntity));
    }


    @Override
    @Transactional
    public void deleteMedicationBatch(Long id) {
        medicationBatchEntityRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MedicationBatchEntityDTO removeLocationFromMedicationBatch(Long medicationBatchId, Long locationId) {
        MedicationBatchEntity medicationBatch = medicationBatchEntityRepository.findById(medicationBatchId).orElseThrow(() -> new RuntimeException("No medication batch entity found with id "+medicationBatchId));
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new RuntimeException("No location found with id "+locationId));

        medicationBatch.setLocation(null);
        location.getMedicationBatchList().remove(medicationBatch);

        return medicationBatchEntityMapper.toDTO(saveMedicationBatch(medicationBatch));
    }
}
