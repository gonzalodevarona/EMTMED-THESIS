package com.emt.med.location;

import org.springframework.stereotype.Service;

@Service
public interface LocationService {


    LocationDTO getLocationDTOById(Long locationId);

    Location saveLocation(Location location);


    void deleteLocation(Long id);
}
