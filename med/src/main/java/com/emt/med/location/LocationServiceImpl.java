package com.emt.med.location;

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
    public LocationDTO getLocationDTOById(Long locationId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new RuntimeException("No location found with id "+locationId));
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
