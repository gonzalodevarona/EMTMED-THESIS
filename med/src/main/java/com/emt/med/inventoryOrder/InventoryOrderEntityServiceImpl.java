package com.emt.med.inventoryOrder;

import com.emt.med.batch.BatchEntity;
import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.order.OrderStatus;
import com.emt.med.pharmacy.PharmacyCategory;
import com.emt.med.pharmacy.PharmacyEntityService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryOrderEntityServiceImpl implements InventoryOrderEntityService{

    private InventoryOrderEntityRepository inventoryOrderEntityRepository;
    private PharmacyEntityService pharmacyEntityService;
    static InventoryOrderEntityMapper inventoryOrderEntityMapper = Mappers.getMapper(InventoryOrderEntityMapper.class);

    public InventoryOrderEntityServiceImpl(InventoryOrderEntityRepository inventoryOrderEntityRepository, PharmacyEntityService pharmacyEntityService) {
        this.inventoryOrderEntityRepository = inventoryOrderEntityRepository;
        this.pharmacyEntityService = pharmacyEntityService;
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
        if (inventoryOrderEntity.getSource() == null || inventoryOrderEntity.getDestination() == null){
            throw new RuntimeException("A new inventory order must have a source and a destination");
        } else if (inventoryOrderEntity.getSource().getId() == inventoryOrderEntity.getDestination().getId()){
            throw new RuntimeException("A new inventory order cannot have the same source and destination");
        }
        inventoryOrderEntity = inventoryOrderEntityRepository.save(inventoryOrderEntity);
        return inventoryOrderEntityMapper.toDTO(inventoryOrderEntity);
    }

    @Override
    @Transactional
    public InventoryOrderEntityDTO processNewMedicationBatches(MedicineEntity medicine){

        InventoryOrderEntity entryInventoryOrder = new InventoryOrderEntity();
        entryInventoryOrder.setOperation(InventoryOrderOperation.ENTRY);
        entryInventoryOrder.setStatus(OrderStatus.COMPLETED);
        entryInventoryOrder.setPractitionerId(medicine.getIdNumberCreatedBy());
        entryInventoryOrder.setAuthoredOn(LocalDateTime.now());
        entryInventoryOrder.setSource(pharmacyEntityService.getPharmacyByCategory(PharmacyCategory.WAREHOUSE));
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
    public InventoryOrderEntityDTO processNewBatches(ConsumableEntity consumable){

        InventoryOrderEntity entryInventoryOrder = new InventoryOrderEntity();
        entryInventoryOrder.setOperation(InventoryOrderOperation.ENTRY);
        entryInventoryOrder.setStatus(OrderStatus.COMPLETED);
        entryInventoryOrder.setPractitionerId(consumable.getIdNumberCreatedBy());
        entryInventoryOrder.setAuthoredOn(LocalDateTime.now());
        entryInventoryOrder.setSource(pharmacyEntityService.getPharmacyByCategory(PharmacyCategory.WAREHOUSE));
        entryInventoryOrder.setDestination(pharmacyEntityService.getPharmacyByCategory(PharmacyCategory.PRINCIPAL));
        entryInventoryOrder = inventoryOrderEntityRepository.save(entryInventoryOrder);
        for (BatchEntity batch:consumable.getBatches()) {
            if(entryInventoryOrder.getBatches() == null){
                entryInventoryOrder.setBatches(new ArrayList<BatchEntity>());
            }
            if(batch.getInventoryOrders() == null){
                batch.setInventoryOrders(new ArrayList<InventoryOrderEntity>());
            }
            entryInventoryOrder.getBatches().add(batch);
            batch.getInventoryOrders().add(entryInventoryOrder);
        }

        return inventoryOrderEntityMapper.toDTO(inventoryOrderEntityRepository.save(entryInventoryOrder));
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
