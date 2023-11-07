package com.emt.med.supplyOrder;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplyOrderEntityService {

    SupplyOrderEntityDTO getSupplyOrderEntityById(Long supplyOrderEntityId);
    List<SupplyOrderEntityDTO> getAllSupplyOrders();
    SupplyOrderEntityDTO addSupplyOrder(SupplyOrderEntityDTO supplyOrderDTO);

    SupplyOrderEntityDTO updateSupplyOrder(SupplyOrderEntityDTO supplyOrderDTO);

    void deleteSupplyOrder(Long id);
}
