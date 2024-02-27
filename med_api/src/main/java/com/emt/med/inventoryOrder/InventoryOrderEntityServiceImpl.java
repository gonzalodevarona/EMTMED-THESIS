package com.emt.med.inventoryOrder;

import com.emt.med.batch.BatchEntity;
import com.emt.med.batch.BatchEntityRepository;
import com.emt.med.batch.BatchEntityService;
import com.emt.med.consumable.ConsumableEntityService;
import com.emt.med.disposalStation.DisposalStationEntity;
import com.emt.med.disposalStation.DisposalStationEntityRepository;
import com.emt.med.disposalStation.DisposalStationEntityService;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicationBatch.MedicationBatchEntityRepository;
import com.emt.med.medicationBatch.MedicationBatchEntityService;
import com.emt.med.medicine.MedicineEntityService;
import com.emt.med.order.OrderStatus;
import com.emt.med.pharmacy.PharmacyCategory;
import com.emt.med.pharmacy.PharmacyEntity;
import com.emt.med.pharmacy.PharmacyEntityRepository;
import com.emt.med.pharmacy.PharmacyEntityService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryOrderEntityServiceImpl implements InventoryOrderEntityService{

    private InventoryOrderEntityRepository inventoryOrderEntityRepository;

    private MedicationBatchEntityRepository medicationBatchEntityRepository;
    private BatchEntityRepository batchEntityRepository;
    private PharmacyEntityService pharmacyEntityService;
    private PharmacyEntityRepository pharmacyEntityRepository;
    private DisposalStationEntityService disposalStationEntityService;
    private DisposalStationEntityRepository disposalStationEntityRepository;
    private MedicineEntityService medicineEntityService;
    private ConsumableEntityService consumableEntityService;
    private BatchEntityService batchEntityService;
    private MedicationBatchEntityService medicationBatchEntityService;
    static InventoryOrderEntityMapper inventoryOrderEntityMapper = Mappers.getMapper(InventoryOrderEntityMapper.class);

    public InventoryOrderEntityServiceImpl(InventoryOrderEntityRepository inventoryOrderEntityRepository, MedicationBatchEntityRepository medicationBatchEntityRepository, BatchEntityRepository batchEntityRepository, PharmacyEntityService pharmacyEntityService, PharmacyEntityRepository pharmacyEntityRepository, DisposalStationEntityService disposalStationEntityService, DisposalStationEntityRepository disposalStationEntityRepository, MedicineEntityService medicineEntityService, ConsumableEntityService consumableEntityService, BatchEntityService batchEntityService, MedicationBatchEntityService medicationBatchEntityService) {
        this.inventoryOrderEntityRepository = inventoryOrderEntityRepository;
        this.medicationBatchEntityRepository = medicationBatchEntityRepository;
        this.batchEntityRepository = batchEntityRepository;
        this.pharmacyEntityService = pharmacyEntityService;
        this.pharmacyEntityRepository = pharmacyEntityRepository;
        this.disposalStationEntityService = disposalStationEntityService;
        this.disposalStationEntityRepository = disposalStationEntityRepository;
        this.medicineEntityService = medicineEntityService;
        this.consumableEntityService = consumableEntityService;
        this.batchEntityService = batchEntityService;
        this.medicationBatchEntityService = medicationBatchEntityService;
    }

    @Override
    public InventoryOrderEntityDTO getInventoryOrderEntityById(Long inventoryOrderEntityId) {
        InventoryOrderEntity inventoryOrderEntity = inventoryOrderEntityRepository.findById(inventoryOrderEntityId).orElseThrow(() -> new RuntimeException("No inventory order found with id "+inventoryOrderEntityId));
        return inventoryOrderEntityMapper.toDTO(inventoryOrderEntity);
    }

    @Override
    public List<InventoryOrderEntityDTO> getAllInventoryOrders() {
        return inventoryOrderEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "status")).stream().map(inventoryOrderEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public InventoryOrderEntityDTO addInventoryOrder(InventoryOrderEntityDTO inventoryOrderEntityDTO) {

        if (inventoryOrderEntityDTO.getId() != null) {
            throw new RuntimeException("A new inventory order cannot already have an ID");
        }
//        inventoryOrderEntityDTO.setStatus(OrderStatus.OPEN);
        InventoryOrderEntity inventoryOrderEntity = inventoryOrderEntityMapper.toEntity(inventoryOrderEntityDTO);
        if ( inventoryOrderEntity.getDestination() == null){
            throw new RuntimeException("A new inventory order must have a destination");
        }
        List<MedicationBatchEntity> extractedMedicationBatches = new ArrayList<MedicationBatchEntity>(inventoryOrderEntity.getMedicationBatches());
        List<BatchEntity> extractedBatches = new ArrayList<BatchEntity>(inventoryOrderEntity.getBatches());

        inventoryOrderEntity.getMedicationBatches().clear();
        inventoryOrderEntity.getBatches().clear();

        inventoryOrderEntity = inventoryOrderEntityRepository.save(inventoryOrderEntity);

        List<MedicationBatchEntity> addedMedicationBatches = new ArrayList<MedicationBatchEntity>();
        List<BatchEntity> addedBatches = new ArrayList<BatchEntity>();
        for (MedicationBatchEntity medicationBatch:extractedMedicationBatches) {
            Long medicationBatchId = medicationBatch.getId();
            MedicationBatchEntity foundMedicationBatchEntity = medicationBatchEntityRepository.findById(medicationBatch.getId()).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchId));

            medicationBatch.setMedicine(foundMedicationBatchEntity.getMedicine());
            medicationBatch.setLocation(foundMedicationBatchEntity.getLocation());


            if(foundMedicationBatchEntity.getQuantity() != medicationBatch.getQuantity()){
                medicationBatch.setId(null);
                medicationBatch.setIsAvailable(false);
                medicationBatch.setParentBatchId(foundMedicationBatchEntity.getId());


            }else{
                medicationBatch = foundMedicationBatchEntity;
            }

            if(medicationBatch.getInventoryOrders() == null){
                medicationBatch.setInventoryOrders(new ArrayList<InventoryOrderEntity>());
            }

            medicationBatchEntityRepository.save(medicationBatch);
            medicationBatch.getInventoryOrders().add(inventoryOrderEntity);
            medicationBatchEntityRepository.save(medicationBatch);
            addedMedicationBatches.add(medicationBatch);
        }

        for (BatchEntity batch:extractedBatches) {
            BatchEntity foundBatchEntity = batchEntityRepository.findById(batch.getId()).orElseThrow(() -> new RuntimeException("No batch found with id "+batch.getId()));

            batch.setConsumable(foundBatchEntity.getConsumable());
            batch.setLocation(foundBatchEntity.getLocation());


            if(foundBatchEntity.getQuantity() != batch.getQuantity()){
                batch.setId(null);
                batch.setIsAvailable(false);
                batch.setParentBatchId(foundBatchEntity.getId());
                if(batch.getInventoryOrders() == null){
                    batch.setInventoryOrders(new ArrayList<InventoryOrderEntity>());
                }

            }

            batchEntityRepository.save(batch);
            batch.getInventoryOrders().add(inventoryOrderEntity);
            batchEntityRepository.save(batch);
            addedBatches.add(batch);
        }
        inventoryOrderEntity.getMedicationBatches().clear();
        inventoryOrderEntity.getMedicationBatches().addAll(addedMedicationBatches);

        inventoryOrderEntity.getBatches().clear();
        inventoryOrderEntity.getBatches().addAll(addedBatches);

        inventoryOrderEntity = inventoryOrderEntityRepository.save(inventoryOrderEntity);
        return inventoryOrderEntityMapper.toDTO(inventoryOrderEntity);
    }

    @Override
    @Transactional
    public InventoryOrderEntityDTO changeInventoryOrderStatus(Long inventoryOrderId, OrderStatus orderStatus) {
        InventoryOrderEntity existingInventoryOrderEntity = inventoryOrderEntityRepository.findById(inventoryOrderId).orElseThrow(() -> new RuntimeException("No inventory order found with id "+inventoryOrderId));
        if(existingInventoryOrderEntity.getStatus() == OrderStatus.CANCELLED || existingInventoryOrderEntity.getStatus() == OrderStatus.COMPLETED){
            throw new RuntimeException("Error: completed or cancelled order cannot change its status");
        }else if(existingInventoryOrderEntity.getStatus() == orderStatus){
            throw new RuntimeException("Error: cannot change to same order status");
        } else{

            PharmacyEntity principalPharmacy = pharmacyEntityService.getPharmacyByCategory(PharmacyCategory.PRINCIPAL);

            if(orderStatus == OrderStatus.COMPLETED && existingInventoryOrderEntity.getOperation() == InventoryOrderOperation.TRANSFER) {
                for (BatchEntity batchEntity : existingInventoryOrderEntity.getBatches()) {
                    try {
                        BatchEntity foundBatch = batchEntityRepository.findById(batchEntity.getId()).orElseThrow(() -> new RuntimeException("No batch found with id "+batchEntity.getId()));
                        PharmacyEntity foundPharmacy = pharmacyEntityService.getPharmacyEntityById(foundBatch.getLocation().getId());
                        if(foundPharmacy.getCategory() != PharmacyCategory.WAREHOUSE) {
                            if (foundBatch.getParentBatchId() != null) {
                                batchEntityService.decrementBatchQuantity(foundBatch.getParentBatchId(), foundBatch.getQuantity());
                            }

                            foundBatch.setLocation(existingInventoryOrderEntity.getDestination());
                            foundBatch.setIsAvailable(true);

                            batchEntityRepository.save(foundBatch);
                            consumableEntityService.recalculateQuantity(foundBatch.getConsumable().getId());
                        }
                    } catch(RuntimeException ex){
                        ex.printStackTrace();
                    }


                }

                for (MedicationBatchEntity medicationBatchEntity : existingInventoryOrderEntity.getMedicationBatches()) {
                    try{
                        MedicationBatchEntity foundMedicationBatch =  medicationBatchEntityRepository.findById(medicationBatchEntity.getId()).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchEntity.getId()));

                        PharmacyEntity foundPharmacy = pharmacyEntityService.getPharmacyEntityById(foundMedicationBatch.getLocation().getId());
                        if(foundPharmacy.getCategory() != PharmacyCategory.WAREHOUSE) {
                            if (foundMedicationBatch.getParentBatchId() != null) {
                                medicationBatchEntityService.decrementMedicationBatchQuantity(foundMedicationBatch.getParentBatchId(), foundMedicationBatch.getQuantity());
                            }

                            foundMedicationBatch.setLocation(existingInventoryOrderEntity.getDestination());
                            foundMedicationBatch.setIsAvailable(true);

                            medicationBatchEntityRepository.save(foundMedicationBatch);
                            medicineEntityService.recalculateQuantity(foundMedicationBatch.getMedicine().getId());
                        }
                    }catch(RuntimeException ex){
                        ex.printStackTrace();
                    }

                }
            } else if(orderStatus == OrderStatus.COMPLETED && existingInventoryOrderEntity.getOperation() == InventoryOrderOperation.EXIT) {

                    Optional<PharmacyEntity> foundPharmacy = pharmacyEntityRepository.findById(existingInventoryOrderEntity.getDestination().getId());
                    Optional<DisposalStationEntity> foundDisposalStation = disposalStationEntityRepository.findById(existingInventoryOrderEntity.getDestination().getId());

                    if ((foundPharmacy.isPresent() && foundPharmacy.get().getCategory() == PharmacyCategory.WAREHOUSE) ||
                            foundDisposalStation.isPresent()){
                        for (BatchEntity batchEntity : existingInventoryOrderEntity.getBatches()) {
                            BatchEntity foundBatch = batchEntityRepository.findById(batchEntity.getId()).orElseThrow(() -> new RuntimeException("No batch found with id "+batchEntity.getId()));
                            foundBatch.setLocation(existingInventoryOrderEntity.getDestination());
                            foundBatch.setIsAvailable(false);


                            batchEntityRepository.save(foundBatch);
                            consumableEntityService.recalculateQuantity(foundBatch.getConsumable().getId());
                        }
                        for (MedicationBatchEntity medicationBatchEntity : existingInventoryOrderEntity.getMedicationBatches()) {
                            MedicationBatchEntity foundMedicationBatch =  medicationBatchEntityRepository.findById(medicationBatchEntity.getId()).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchEntity.getId()));
                            foundMedicationBatch.setLocation(existingInventoryOrderEntity.getDestination());
                            foundMedicationBatch.setIsAvailable(false);


                            medicationBatchEntityRepository.save(foundMedicationBatch);
                            medicineEntityService.recalculateQuantity(foundMedicationBatch.getMedicine().getId());
                        }
                    }


            }else if(orderStatus == OrderStatus.COMPLETED && existingInventoryOrderEntity.getOperation() == InventoryOrderOperation.ENTRY) {
                existingInventoryOrderEntity.setDestination(principalPharmacy);
                for (BatchEntity batchEntity : existingInventoryOrderEntity.getBatches()) {
                    BatchEntity foundBatch = batchEntityRepository.findById(batchEntity.getId()).orElseThrow(() -> new RuntimeException("No batch found with id "+batchEntity.getId()));
                    foundBatch.setLocation(principalPharmacy);
                    foundBatch.setIsAvailable(false);

                    batchEntityRepository.save(foundBatch);
                    consumableEntityService.recalculateQuantity(foundBatch.getConsumable().getId());
                }
                for (MedicationBatchEntity medicationBatchEntity : existingInventoryOrderEntity.getMedicationBatches()) {
                    MedicationBatchEntity foundMedicationBatch =  medicationBatchEntityRepository.findById(medicationBatchEntity.getId()).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchEntity.getId()));
                    foundMedicationBatch.setLocation(principalPharmacy);
                    foundMedicationBatch.setIsAvailable(false);

                    medicationBatchEntityRepository.save(foundMedicationBatch);
                    medicineEntityService.recalculateQuantity(foundMedicationBatch.getMedicine().getId());
                }
            }

            existingInventoryOrderEntity.setStatus(orderStatus);
            return inventoryOrderEntityMapper.toDTO(inventoryOrderEntityRepository.save(existingInventoryOrderEntity));
        }
    }


    @Override
    @Transactional
    public InventoryOrderEntityDTO updateInventoryOrder(InventoryOrderEntityDTO inventoryOrderEntityDTO) {
        InventoryOrderEntity existingBatchEntity = inventoryOrderEntityRepository.findById(inventoryOrderEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No inventory order found with id "+inventoryOrderEntityDTO.getId()));

        if(existingBatchEntity.getStatus() != inventoryOrderEntityDTO.getStatus() &&
                ( existingBatchEntity.getAuthoredOn() == null ||
                existingBatchEntity.getOperation() == null ||
                existingBatchEntity.getPractitionerId() == null )){

            throw new RuntimeException(" Error:Cannot change order status if there are no related entities");
        } else{
            inventoryOrderEntityMapper.updateInventoryOrderFromDTO(inventoryOrderEntityDTO, existingBatchEntity);
            return inventoryOrderEntityMapper.toDTO(inventoryOrderEntityRepository.save(existingBatchEntity));

        }
    }

    @Override
    @Transactional
    public void deleteInventoryOrder(Long id) {
        inventoryOrderEntityRepository.deleteById(id);
    }
}
