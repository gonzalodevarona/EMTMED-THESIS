package com.emt.med.order;

import com.emt.med.countingUnit.CountingUnitEntityMapper;
import com.emt.med.inventoryOrder.InventoryOrderEntity;
import com.emt.med.inventoryOrder.InventoryOrderEntityDTO;
import com.emt.med.location.LocationMapper;

import com.emt.med.supply.SupplyMapper;
import com.emt.med.supplyOrder.SupplyOrderEntity;
import com.emt.med.supplyOrder.SupplyOrderEntityDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(uses = {SupplyMapper.class, LocationMapper.class, CountingUnitEntityMapper.class})
public interface OrderEntityMapper {

    OrderEntityMapper INSTANCE = Mappers.getMapper( OrderEntityMapper.class );
    @SubclassMapping(source = SupplyOrderEntity.class, target = SupplyOrderEntityDTO.class)
    @SubclassMapping(source = InventoryOrderEntity.class, target = InventoryOrderEntityDTO.class)
    OrderEntityDTO toDTO(OrderEntity order);
    @InheritInverseConfiguration
    OrderEntity toEntity(OrderEntityDTO orderDTO);

    @ObjectFactory
    default OrderEntity createOrder(OrderEntityDTO orderDTO) {
        if (orderDTO instanceof SupplyOrderEntityDTO) {
            return new SupplyOrderEntity();
        } else if (orderDTO instanceof InventoryOrderEntityDTO) {
            return new InventoryOrderEntity();
        }
        return null;
    }

}
