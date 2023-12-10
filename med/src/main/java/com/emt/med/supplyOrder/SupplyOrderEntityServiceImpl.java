package com.emt.med.supplyOrder;

import com.emt.med.batch.BatchEntityService;
import com.emt.med.batchRequest.BatchRequestEntity;
import com.emt.med.batchRequest.BatchRequestEntityRepository;
import com.emt.med.medicationBatch.MedicationBatchEntityService;
import com.emt.med.medicationBatchRequest.MedicationBatchRequestEntity;
import com.emt.med.medicationBatchRequest.MedicationBatchRequestEntityRepository;
import com.emt.med.order.OrderStatus;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyOrderEntityServiceImpl implements SupplyOrderEntityService{
    
    private SupplyOrderEntityRepository supplyOrderEntityRepository;

    private MedicationBatchRequestEntityRepository medicationBatchRequestEntityRepository;
    private BatchRequestEntityRepository batchRequestEntityRepository;

    private MedicationBatchEntityService medicationBatchEntityService;

    private BatchEntityService batchEntityService;

    static SupplyOrderEntityMapper supplyOrderEntityMapper = Mappers.getMapper(SupplyOrderEntityMapper.class);


    public SupplyOrderEntityServiceImpl(SupplyOrderEntityRepository supplyOrderEntityRepository, MedicationBatchRequestEntityRepository medicationBatchRequestEntityRepository, BatchRequestEntityRepository batchRequestEntityRepository, MedicationBatchEntityService medicationBatchEntityService, BatchEntityService batchEntityService) {
        this.supplyOrderEntityRepository = supplyOrderEntityRepository;
        this.medicationBatchRequestEntityRepository = medicationBatchRequestEntityRepository;
        this.batchRequestEntityRepository = batchRequestEntityRepository;
        this.medicationBatchEntityService = medicationBatchEntityService;
        this.batchEntityService = batchEntityService;
    }

    @Override
    public SupplyOrderEntityDTO getSupplyOrderEntityById(Long supplyOrderEntityId) {
        SupplyOrderEntity supplyOrderEntity = supplyOrderEntityRepository.findById(supplyOrderEntityId).orElseThrow(() -> new RuntimeException("No supply order with id "+supplyOrderEntityId));
        return supplyOrderEntityMapper.toDTO(supplyOrderEntity);
    }

    @Override
    public List<SupplyOrderEntityDTO> getAllSupplyOrders() {
        return supplyOrderEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "status")).stream().map(supplyOrderEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public SupplyOrderEntityDTO addSupplyOrder(SupplyOrderEntityDTO supplyOrderDTO) {
        if (supplyOrderDTO == null || supplyOrderDTO.getId() != null) {
            throw new RuntimeException("A new supply order cannot already have an ID");
        }

        SupplyOrderEntity supplyOrderEntity = supplyOrderEntityMapper.toEntity(supplyOrderDTO);
        supplyOrderEntity = supplyOrderEntityRepository.save(supplyOrderEntity);
        if(supplyOrderEntity.getMedicationBatchRequests().size()>0){
            for (MedicationBatchRequestEntity medicationBatchRequestEntity: supplyOrderEntity.getMedicationBatchRequests()) {
                medicationBatchRequestEntity.setSupplyOrder(supplyOrderEntity);
                medicationBatchRequestEntityRepository.save(medicationBatchRequestEntity);
            }
        }

        if(supplyOrderEntity.getBatchRequests().size()>0){
            for (BatchRequestEntity batchRequestEntity: supplyOrderEntity.getBatchRequests()) {
                batchRequestEntity.setSupplyOrder(supplyOrderEntity);
                batchRequestEntityRepository.save(batchRequestEntity);
            }
        }



        return supplyOrderEntityMapper.toDTO(supplyOrderEntityRepository.save(supplyOrderEntity));
    }

    @Override
    @Transactional
    public SupplyOrderEntityDTO changeSupplyOrderStatus(Long supplyOrderId, OrderStatus orderStatus) {
        SupplyOrderEntity existingSupplyOrderEntity = supplyOrderEntityRepository.findById(supplyOrderId).orElseThrow(() -> new RuntimeException("No supply order found with id "+supplyOrderId));
        if(existingSupplyOrderEntity.getStatus() == OrderStatus.CANCELLED || existingSupplyOrderEntity.getStatus() == OrderStatus.COMPLETED){
            throw new RuntimeException("Error: completed or cancelled order cannot change its status");
        }else if(existingSupplyOrderEntity.getStatus() == orderStatus){
            throw new RuntimeException("Error: cannot change to same order status");
        } else{

            if(orderStatus == OrderStatus.COMPLETED) {
                for (BatchRequestEntity batchRequestEntity : existingSupplyOrderEntity.getBatchRequests()) {
                    batchEntityService.decrementBatchQuantity(batchRequestEntity.getBatch().getId(), batchRequestEntity.getQuantity());
                }

                for (MedicationBatchRequestEntity medicationBatchRequestEntity : existingSupplyOrderEntity.getMedicationBatchRequests()) {

                    medicationBatchEntityService.decrementMedicationBatchQuantity(medicationBatchRequestEntity.getMedicationBatch().getId(), medicationBatchRequestEntity.getQuantity());

                }
            }

            existingSupplyOrderEntity.setStatus(orderStatus);
            return supplyOrderEntityMapper.toDTO(supplyOrderEntityRepository.save(existingSupplyOrderEntity));
        }
    }

    @Override
    @Transactional
    public void deleteSupplyOrder(Long id) {
        supplyOrderEntityRepository.deleteById(id);
    }
}
