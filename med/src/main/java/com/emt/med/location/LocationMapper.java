package com.emt.med.location;

import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.disposalStation.DisposalStationEntity;
import com.emt.med.disposalStation.DisposalStationEntityDTO;
import com.emt.med.inventoryOrder.InventoryOrderEntityMapper;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.pharmacy.PharmacyEntity;
import com.emt.med.pharmacy.PharmacyEntityDTO;
import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {SupplyMapper.class, InventoryOrderEntityMapper.class})
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper( LocationMapper.class );

    @SubclassMapping( source = DisposalStationEntity.class, target = DisposalStationEntityDTO.class )
    @SubclassMapping( source = PharmacyEntity.class, target = PharmacyEntityDTO.class )
    @Mapping(target = "supplyList", ignore = true)
    @Mapping(target = "destinationList", ignore = true)
    @Mapping(target = "sourceList", ignore = true)
    LocationDTO toDTO(Location location);

    @InheritInverseConfiguration
    @Mapping(target = "supplyList", ignore = true)
    Location toEntity(LocationDTO locationDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "supplyList", ignore = true)
    void updateLocationFromDTO(LocationDTO dto, @MappingTarget Location entity);

    @ObjectFactory
    default Location createLocation(LocationDTO locationDTO) {
        if (locationDTO instanceof DisposalStationEntityDTO) {
            return new DisposalStationEntity();
        } else if (locationDTO instanceof PharmacyEntityDTO) {
            return new PharmacyEntity();
        }
        return null;
    }

    List<LocationDTO> toLocationDTOList(List<Location> locations);

    List<Location> toLocationList(List<LocationDTO> locations);
}
