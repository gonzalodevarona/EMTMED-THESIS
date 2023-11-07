package com.emt.med.supplyOrder;

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

    static SupplyOrderEntityMapper supplyOrderEntityMapper = Mappers.getMapper(SupplyOrderEntityMapper.class);

    public SupplyOrderEntityServiceImpl(SupplyOrderEntityRepository supplyOrderEntityRepository) {
        this.supplyOrderEntityRepository = supplyOrderEntityRepository;
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
        if (supplyOrderDTO.getId() != null) {
            throw new RuntimeException("A new supply order cannot already have an ID");
        }
        SupplyOrderEntity supplyOrderEntity = supplyOrderEntityMapper.toEntity(supplyOrderDTO);
        supplyOrderEntity = supplyOrderEntityRepository.save(supplyOrderEntity);
        return supplyOrderEntityMapper.toDTO(supplyOrderEntity);
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
