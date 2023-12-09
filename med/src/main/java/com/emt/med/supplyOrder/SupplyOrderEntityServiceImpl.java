package com.emt.med.supplyOrder;

import com.emt.med.batch.BatchEntity;
import com.emt.med.batch.BatchEntityRepository;
import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.consumable.ConsumableEntityMapper;
import com.emt.med.consumable.ConsumableEntityRepository;
import com.emt.med.medicationBatch.MedicationBatchEntity;
import com.emt.med.medicationBatch.MedicationBatchEntityRepository;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.medicine.MedicineEntityMapper;
import com.emt.med.medicine.MedicineEntityRepository;
import com.emt.med.supply.Supply;
import com.emt.med.supply.SupplyMapper;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SupplyOrderEntityServiceImpl implements SupplyOrderEntityService{
    
    private SupplyOrderEntityRepository supplyOrderEntityRepository;
    private MedicineEntityRepository medicineEntityRepository;
    private ConsumableEntityRepository consumableEntityRepository;
    private MedicationBatchEntityRepository medicationBatchEntityRepository;
    private BatchEntityRepository batchEntityRepository;


    static SupplyOrderEntityMapper supplyOrderEntityMapper = Mappers.getMapper(SupplyOrderEntityMapper.class);

    static MedicineEntityMapper medicineEntityMapper = Mappers.getMapper(MedicineEntityMapper.class);
    static ConsumableEntityMapper consumableEntityMapper = Mappers.getMapper(ConsumableEntityMapper.class);

    public SupplyOrderEntityServiceImpl(SupplyOrderEntityRepository supplyOrderEntityRepository, MedicineEntityRepository medicineEntityRepository, ConsumableEntityRepository consumableEntityRepository, MedicationBatchEntityRepository medicationBatchEntityRepository, BatchEntityRepository batchEntityRepository) {
        this.supplyOrderEntityRepository = supplyOrderEntityRepository;
        this.medicineEntityRepository = medicineEntityRepository;
        this.consumableEntityRepository = consumableEntityRepository;
        this.medicationBatchEntityRepository = medicationBatchEntityRepository;
        this.batchEntityRepository = batchEntityRepository;
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
        SupplyOrderEntity supplyOrderEntity = supplyOrderEntityMapper.toEntityNoSupplies(supplyOrderDTO);

        Set<Supply> addedSupplies = new HashSet<Supply>();

        if (supplyOrderDTO.getSupplies() != null) {
            for (int i = 0; i < supplyOrderDTO.getSupplies().size(); i++) {
                if (supplyOrderDTO.getSupplies().get(i) instanceof MedicineEntityDTO){
                    MedicineEntity medicine = medicineEntityMapper.toEntity((MedicineEntityDTO) supplyOrderDTO.getSupplies().get(i));

                    if (((MedicineEntityDTO) supplyOrderDTO.getSupplies().get(i)).getBatches() != null && !((MedicineEntityDTO) supplyOrderDTO.getSupplies().get(i)).getBatches().isEmpty()) {
                        Long medicationBatchId = ((MedicineEntityDTO) supplyOrderDTO.getSupplies().get(i)).getBatches().get(0).getId();
                        MedicationBatchEntity medicationBatch = medicationBatchEntityRepository.findById(medicationBatchId).orElseThrow(() -> new RuntimeException("No medication batch found with id "+medicationBatchId));
                        List<MedicationBatchEntity> singleBatch = new ArrayList<>();
                        singleBatch.add(medicationBatch);

                        medicine.setBatches(singleBatch);
                    }
                    addedSupplies.add(medicineEntityRepository.save(medicine));
                } else{
                    ConsumableEntity consumable = consumableEntityMapper.toEntity((ConsumableEntityDTO) supplyOrderDTO.getSupplies().get(i));

                    if (((ConsumableEntityDTO) supplyOrderDTO.getSupplies().get(i)).getBatches() != null && !((ConsumableEntityDTO) supplyOrderDTO.getSupplies().get(i)).getBatches().isEmpty()) {
                        Long batchId = ((ConsumableEntityDTO) supplyOrderDTO.getSupplies().get(i)).getBatches().get(0).getId();
                        BatchEntity batch = batchEntityRepository.findById(batchId).orElseThrow(() -> new RuntimeException("No batch found with id "+batchId));
                        List<BatchEntity> singleBatch = new ArrayList<>();
                        singleBatch.add(batch);

                        consumable.setBatches(singleBatch);
                    }
                    addedSupplies.add(consumableEntityRepository.save(consumable));
                }
            }
        }

        supplyOrderEntity.setSupplies(addedSupplies);
        supplyOrderEntity = supplyOrderEntityRepository.save(supplyOrderEntity);
        return supplyOrderEntityMapper.toDTONoSupplies(supplyOrderEntity);
    }



    @Override
    @Transactional
    public SupplyOrderEntityDTO updateSupplyOrder(SupplyOrderEntityDTO supplyOrderDTO) {
        SupplyOrderEntity existingBatchEntity = supplyOrderEntityRepository.findById(supplyOrderDTO.getId()).orElseThrow(() -> new RuntimeException("No supply order found with id "+supplyOrderDTO.getId()));
        supplyOrderEntityMapper.updateSupplyOrderFromDto(supplyOrderDTO, existingBatchEntity);
        return supplyOrderEntityMapper.toDTO(supplyOrderEntityRepository.save(existingBatchEntity));
    }

    @Override
    @Transactional
    public void deleteSupplyOrder(Long id) {
        supplyOrderEntityRepository.deleteById(id);
    }
}
