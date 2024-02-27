package com.emt.med.supplyOrder;

import com.emt.med.order.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplyOrderEntityService {

    SupplyOrderEntityDTO getSupplyOrderEntityById(Long supplyOrderEntityId);
    List<SupplyOrderEntityDTO> getAllSupplyOrders();
    SupplyOrderEntityDTO addSupplyOrder(SupplyOrderEntityDTO supplyOrderDTO);
    SupplyOrderEntityDTO changeSupplyOrderStatus(Long supplyOrderId, OrderStatus orderStatus);
    void deleteSupplyOrder(Long id);
}
