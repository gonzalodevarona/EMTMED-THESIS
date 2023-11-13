package com.emt.med.inventoryOrder;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryOrderEntityServiceImpl implements InventoryOrderEntityService{

    private InventoryOrderEntityRepository inventoryOrderEntityRepository;

    static InventoryOrderEntityMapper inventoryOrderEntityMapper = Mappers.getMapper(InventoryOrderEntityMapper.class);

    public InventoryOrderEntityServiceImpl(InventoryOrderEntityRepository inventoryOrderEntityRepository) {
        this.inventoryOrderEntityRepository = inventoryOrderEntityRepository;
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
    public InventoryOrderEntityDTO addInventoryOrder(InventoryOrderEntityDTO inventoryOrderEntityDTO) {

        if (inventoryOrderEntityDTO.getId() != null) {
            throw new RuntimeException("A new inventory order cannot already have an ID");
        }
        InventoryOrderEntity inventoryOrderEntity = inventoryOrderEntityMapper.toEntity(inventoryOrderEntityDTO);
        inventoryOrderEntity = inventoryOrderEntityRepository.save(inventoryOrderEntity);
        return inventoryOrderEntityMapper.toDTO(inventoryOrderEntity);
    }

    @Override
    public InventoryOrderEntityDTO updateInventoryOrder(InventoryOrderEntityDTO inventoryOrderEntityDTO) {
        InventoryOrderEntity existingBatchEntity = inventoryOrderEntityRepository.findById(inventoryOrderEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No inventory order found with id "+inventoryOrderEntityDTO.getId()));
        inventoryOrderEntityMapper.updateInventoryOrderFromDTO(inventoryOrderEntityDTO, existingBatchEntity);
        return inventoryOrderEntityMapper.toDTO(inventoryOrderEntityRepository.save(existingBatchEntity));
    }

    @Override
    public void deleteInventoryOrder(Long id) {
        inventoryOrderEntityRepository.deleteById(id);
    }
}
