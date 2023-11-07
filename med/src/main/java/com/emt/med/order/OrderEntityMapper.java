package com.emt.med.order;

import com.emt.med.inventoryOrder.InventoryOrderEntity;
import com.emt.med.inventoryOrder.InventoryOrderEntityDTO;
import com.emt.med.supply.SupplyMapper;
import com.emt.med.supplyOrder.SupplyOrderEntity;
import com.emt.med.supplyOrder.SupplyOrderEntityDTO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = SupplyMapper.class)
public interface OrderEntityMapper {

    OrderEntityMapper INSTANCE = Mappers.getMapper( OrderEntityMapper.class );
    OrderEntityDTO toDTO(OrderEntity order);
    OrderEntity toEntity(OrderEntityDTO orderDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromDTO(OrderEntityDTO dto, @MappingTarget OrderEntity entity);

    @ObjectFactory
    default OrderEntity createOrder(OrderEntityDTO orderDTO) {
        if (orderDTO instanceof SupplyOrderEntityDTO) {
            return new SupplyOrderEntity();
        } else if (orderDTO instanceof InventoryOrderEntityDTO) {
            return new InventoryOrderEntity();
        }
        return null;
    }

    List<OrderEntityDTO> toOrderDTOList(List<OrderEntity> orders);

    List<OrderEntity> toOrderList(List<OrderEntityDTO> orders);
}
