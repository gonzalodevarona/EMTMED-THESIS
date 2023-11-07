package com.emt.med.location;

import com.emt.med.consumable.ConsumableEntity;
import com.emt.med.consumable.ConsumableEntityDTO;
import com.emt.med.disposalStation.DisposalStationEntity;
import com.emt.med.disposalStation.DisposalStationEntityDTO;
import com.emt.med.medicine.MedicineEntity;
import com.emt.med.medicine.MedicineEntityDTO;
import com.emt.med.pharmacy.PharmacyEntity;
import com.emt.med.pharmacy.PharmacyEntityDTO;
import com.emt.med.supply.Supply;
import com.emt.med.supply.SupplyDTO;
import com.emt.med.supply.SupplyMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper( LocationMapper.class );
    LocationDTO toDTO(Location location);

    Location toEntity(LocationDTO locationDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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
