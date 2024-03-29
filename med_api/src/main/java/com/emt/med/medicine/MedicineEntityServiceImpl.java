package com.emt.med.medicine;

import com.emt.med.countingUnit.CountingUnitEntityRepository;
import com.emt.med.inventoryOrder.*;
import com.emt.med.location.LocationRepository;
import com.emt.med.medicationBatch.*;
import com.emt.med.order.OrderStatus;
import com.emt.med.pharmacy.PharmacyCategory;
import com.emt.med.pharmacy.PharmacyEntityService;
import com.emt.med.supply.SupplyService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineEntityServiceImpl implements MedicineEntityService {
    
    private MedicineEntityRepository medicineEntityRepository;

    private SupplyService supplyService;

    private PharmacyEntityService pharmacyEntityService;

    private MedicationBatchEntityService medicationBatchEntityService;

    private InventoryOrderEntityRepository inventoryOrderEntityRepository;

    private MedicationBatchEntityRepository medicationBatchEntityRepository;

    private LocationRepository locationRepository;

    private CountingUnitEntityRepository countingUnitEntityRepository;

    static InventoryOrderEntityMapper inventoryOrderEntityMapper = Mappers.getMapper(InventoryOrderEntityMapper.class);

    static MedicineEntityMapper medicineEntityMapper = Mappers.getMapper(MedicineEntityMapper.class);
    static MedicationBatchEntityMapper medicationBatchEntityMapper = Mappers.getMapper(MedicationBatchEntityMapper.class);

    public MedicineEntityServiceImpl(MedicineEntityRepository medicineEntityRepository, SupplyService supplyService, PharmacyEntityService pharmacyEntityService, MedicationBatchEntityService medicationBatchEntityService, InventoryOrderEntityRepository inventoryOrderEntityRepository, MedicationBatchEntityRepository medicationBatchEntityRepository, LocationRepository locationRepository, CountingUnitEntityRepository countingUnitEntityRepository) {
        this.medicineEntityRepository = medicineEntityRepository;
        this.supplyService = supplyService;
        this.pharmacyEntityService = pharmacyEntityService;
        this.medicationBatchEntityService = medicationBatchEntityService;
        this.inventoryOrderEntityRepository = inventoryOrderEntityRepository;
        this.medicationBatchEntityRepository = medicationBatchEntityRepository;
        this.locationRepository = locationRepository;
        this.countingUnitEntityRepository = countingUnitEntityRepository;
    }

    @Override
    public MedicineEntityDTO getMedicineEntityDTOById(Long medicineEntityId) {
        MedicineEntity medicineEntity = medicineEntityRepository.findById(medicineEntityId).orElseThrow(() -> new RuntimeException("No medicine found with id "+medicineEntityId));
        return medicineEntityMapper.toDTO(medicineEntity);
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
    public List<MedicineEntityDTO> getAllMedicinesInStock() {
        List<MedicineEntity> medicinesInStock = medicineEntityRepository.findByQuantityGreaterThanEqualOrderByQuantityDesc(1L);
        List<MedicineEntity> addedMedicines = new ArrayList<MedicineEntity>();
        List<MedicationBatchEntity> addedMedicationBatches = new ArrayList<MedicationBatchEntity>();

        for (MedicineEntity medicineInStock:medicinesInStock) {
            addedMedicationBatches.clear();
            for (MedicationBatchEntity medicationBatch : medicineInStock.getBatches()) {
                if(medicationBatch.getIsAvailable()){
                    addedMedicationBatches.add(medicationBatch);
                }
            }
            medicineInStock.getBatches().clear();
            medicineInStock.getBatches().addAll(addedMedicationBatches);
            addedMedicines.add(medicineInStock);
        }



        return addedMedicines.stream().map(medicineEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<MedicineEntityDTO> getAllMedicinesNoBatches() {
        return medicineEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "activePharmaceuticalIngredient")).stream().map(medicineEntityMapper::toDTONoBatches).collect(Collectors.toCollection(ArrayList::new));

    }

    @Override
    @Transactional
    public MedicineEntityDTO addMedicine(MedicineEntityDTO medicineEntityDTO) {
        if (medicineEntityDTO.getId() != null) {
            throw new RuntimeException("A new medicine cannot already have an ID");
        }
        MedicineEntity medicineEntity = medicineEntityMapper.toEntity((MedicineEntityDTO) medicineEntityDTO);
        medicineEntity = (MedicineEntity) supplyService.addRelationships(medicineEntity);

        addMedicationBatchesToMedicine(medicineEntity.getBatches(), medicineEntity);

        for (MedicationBatchEntity medicationBatchEntity: medicineEntity.getBatches()) {
            medicationBatchEntity.setLocation(pharmacyEntityService.getPharmacyByCategory(PharmacyCategory.PRINCIPAL));
            medicationBatchEntityRepository.save(medicationBatchEntity);
        }

        medicineEntity = medicineEntityRepository.save(medicineEntity);
        processNewMedicationBatches(medicineEntity);

        return medicineEntityMapper.toDTO(medicineEntity);
    }

    @Override
    @Transactional
    public InventoryOrderEntityDTO processNewMedicationBatches(MedicineEntity medicine){

        InventoryOrderEntity entryInventoryOrder = new InventoryOrderEntity();
        entryInventoryOrder.setOperation(InventoryOrderOperation.ENTRY);
        entryInventoryOrder.setStatus(OrderStatus.COMPLETED);
        entryInventoryOrder.setPractitionerId(medicine.getIdNumberCreatedBy());
        entryInventoryOrder.setAuthoredOn(LocalDateTime.now());
        entryInventoryOrder.setDestination(pharmacyEntityService.getPharmacyByCategory(PharmacyCategory.PRINCIPAL));
        entryInventoryOrder = inventoryOrderEntityRepository.save(entryInventoryOrder);
        for (MedicationBatchEntity medicationBatch:medicine.getBatches()) {
            if(entryInventoryOrder.getMedicationBatches() == null){
                entryInventoryOrder.setMedicationBatches(new ArrayList<MedicationBatchEntity>());
            }
            if(medicationBatch.getInventoryOrders() == null){
                medicationBatch.setInventoryOrders(new ArrayList<InventoryOrderEntity>());
            }
            entryInventoryOrder.getMedicationBatches().add(medicationBatch);
            medicationBatch.getInventoryOrders().add(entryInventoryOrder);
        }

        return inventoryOrderEntityMapper.toDTO(inventoryOrderEntityRepository.save(entryInventoryOrder));
    }

    @Override
    @Transactional
    public MedicineEntityDTO recalculateQuantity(Long medicineEntityId) {
        MedicineEntity foundMedicine =  getMedicineEntityById(medicineEntityId);
        Long totalQuantity = 0L;
        for (MedicationBatchEntity medicationBatchEntity: foundMedicine.getBatches()) {
            if(medicationBatchEntity.getIsAvailable()){
                totalQuantity +=medicationBatchEntity.getQuantity();
            }
        }

        foundMedicine.setQuantity(totalQuantity);

        return medicineEntityMapper.toDTO(medicineEntityRepository.save(foundMedicine));
    }




    @Override
    @Transactional
    public MedicineEntityDTO removeCountingUnitFromMedicine(Long medicineEntityId) {
        return medicineEntityMapper.toDTO(saveMedicineEntity((MedicineEntity) supplyService.removeCountingUnitFromSupply(getMedicineEntityById(medicineEntityId))));
    }


    @Override
    @Transactional
    public MedicineEntity addMedicationBatchesToMedicine(List<MedicationBatchEntity> medicationBatchEntities, MedicineEntity medicine){

        if (medicationBatchEntities != null && medicationBatchEntities.size()>0) {

            if (medicine.getBatches() == null) {
                medicine.setBatches(new ArrayList<MedicationBatchEntity>());
            }

            List<MedicationBatchEntity> addedBatches = new ArrayList<MedicationBatchEntity>();

            for (MedicationBatchEntity medicationBatchEntity : medicationBatchEntities) {
                if (medicationBatchEntity.getId() == null) {
                    medicationBatchEntity.setIsAvailable(true);
                    medicationBatchEntity =  medicationBatchEntityRepository.save(medicationBatchEntity);
                }

                Long medicationBatchId = medicationBatchEntity.getId();

                MedicationBatchEntity foundMedicationBatchEntity = medicationBatchEntityRepository.findById(medicationBatchId).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchId));

                if(foundMedicationBatchEntity.getMedicine() == null){
                    foundMedicationBatchEntity.setMedicine(medicine);

                    addedBatches.add(foundMedicationBatchEntity);
                } else{
                    medicationBatchEntityService.updateMedicationBatch(medicationBatchEntity);
                    continue;
                }

            }

            medicine.getBatches().clear();
            medicine.getBatches().addAll(addedBatches);
        }

        return medicineEntityRepository.save(medicine);
    }





    @Override
    @Transactional
    public MedicineEntityDTO removeMedicationBatchFromMedicine(Long medicineId, Long medicationBatchId) {
        MedicineEntity medicine = medicineEntityRepository.findById(medicineId).orElseThrow(() -> new RuntimeException("No medicine entity found with id "+medicineId));
        MedicationBatchEntity medicationBatchEntity = medicationBatchEntityRepository.findById(medicationBatchId).orElseThrow(() -> new RuntimeException("No medication batch entity found with id "+medicationBatchId));

        medicine.getBatches().remove(medicationBatchEntity);

        medicationBatchEntity.setMedicine(null);

        return medicineEntityMapper.toDTO(saveMedicineEntity(medicine));
    }


    @Override
    @Transactional
    public MedicineEntity saveMedicineEntity(MedicineEntity medicineEntity) {
        return medicineEntityRepository.save(medicineEntity);
    }

    @Override
    @Transactional
    public MedicineEntityDTO updateMedicine(MedicineEntityDTO medicineEntityDTO) {
        MedicineEntity existingMedicine = medicineEntityRepository.findById(medicineEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No medicine found with id "+medicineEntityDTO.getId()));


//        if (medicineEntityDTO.getName() != null) {
//            existingMedicine.setName(medicineEntityDTO.getName());
//        }

        if (medicineEntityDTO.getQuantity() != null) {
            existingMedicine.setQuantity(medicineEntityDTO.getQuantity());
        }

//        if (medicineEntityDTO.getCountingUnit() != null) {
//            CountingUnitEntity foundCountingUnit = countingUnitEntityRepository.findById(medicineEntityDTO.getWeightUnit().getId()).orElseThrow(() -> new RuntimeException("No counting unit found with id "+medicineEntityDTO.getCountingUnit().getId()));
//            existingMedicine.setCountingUnit(foundCountingUnit);
//        }
//        if (medicineEntityDTO.getActivePharmaceuticalIngredient() != null) {
//            existingMedicine.setActivePharmaceuticalIngredient(medicineEntityDTO.getActivePharmaceuticalIngredient());
//        }
//        if (medicineEntityDTO.getConcentration() != null) {
//            existingMedicine.setConcentration(medicineEntityDTO.getConcentration());
//        }
        if (medicineEntityDTO.getBatches() != null) {


            List<MedicationBatchEntity> batches = medicationBatchEntityMapper.map(medicineEntityDTO.getBatches());
            for (MedicationBatchEntity batch : batches) {
                if(batch.getId() != null){
                    MedicationBatchEntity foundMedicationBatch = medicationBatchEntityRepository.findById(batch.getId()).orElseThrow(() -> new RuntimeException("No medication batch entity found with id "+batch.getId()));
                    batch.setMedicine(foundMedicationBatch.getMedicine());
                }
            }
            addMedicationBatchesToMedicine( batches, existingMedicine);
        }

        return medicineEntityMapper.toDTO(medicineEntityRepository.save(existingMedicine));
    }


    @Override
    @Transactional
    public void deleteMedicine(Long id) {
            medicineEntityRepository.deleteById(id);
    }


}
