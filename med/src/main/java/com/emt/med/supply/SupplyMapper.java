package com.emt.med.supply;

import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.order.OrderEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( uses = OrderEntityMapper.class)
public interface SupplyMapper {

    SupplyMapper INSTANCE = Mappers.getMapper( SupplyMapper.class );
    SupplyDTO toDTO(Supply supply);

    Supply toEntity(SupplyDTO supplyDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSupplyFromDTO(SupplyDTO dto, @MappingTarget Supply entity);

    @ObjectFactory
    default Supply createSupply(SupplyDTO supplyDTO) {
        if (supplyDTO instanceof MedicineEntityDTO) {
            return new MedicineEntity();
        } else if (supplyDTO instanceof ConsumableEntityDTO) {
            return new ConsumableEntity();
        }
        return null;
    }

    List<SupplyDTO> toSupplyDTOList(List<Supply> supplies);

    List<Supply> toSupplyList(List<SupplyDTO> supplies);
}
