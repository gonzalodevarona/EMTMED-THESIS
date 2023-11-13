package com.emt.med.location;

import com.emt.med.inventoryOrder.InventoryOrderEntity;
import com.emt.med.inventoryOrder.InventoryOrderEntityDTO;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService{
    private LocationRepository locationRepository;
    LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location getLocationById(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(() -> new RuntimeException("No location found with id "+locationId));
    }

    @Override
    public LocationDTO getLocationDTOById(Long locationId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new RuntimeException("No location found with id "+locationId));
        return locationMapper.toDTO(location);
    }


    @Override
    @Transactional
    public LocationDTO addLocation(LocationDTO locationDTO) {
        if (locationDTO.getId() != null) {
            throw new RuntimeException("A new location cannot already have an ID");
        }
        Location location = locationMapper.toEntity(locationDTO);
        location = saveLocation(location);
        return locationMapper.toDTO(location);
    }

    @Override
    @Transactional
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }


    @Override
    @Transactional
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }


}
