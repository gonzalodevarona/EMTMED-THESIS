package com.emt.med.supply;

import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.order.OrderEntityMapper;
import com.emt.med.weightUnit.WeightUnitEntityMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper( uses = {OrderEntityMapper.class, WeightUnitEntityMapper.class})
public interface SupplyMapper {
    SupplyMapper INSTANCE = Mappers.getMapper( SupplyMapper.class );
    @SubclassMapping( source = MedicineEntity.class, target = MedicineEntityDTO.class )
    @SubclassMapping( source = ConsumableEntity.class, target = ConsumableEntityDTO.class )
    @Mapping(target = "orders", ignore = true)
    SupplyDTO toDTO(Supply supply);

    @InheritInverseConfiguration
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


    List<SupplyDTO> map(List<Supply> supplies);


}
