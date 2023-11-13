package com.emt.med.location;

import com.emt.med.medicine.MedicineEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {

    Location getLocationById(Long locationId);
    LocationDTO getLocationDTOById(Long locationId);
    LocationDTO addLocation(LocationDTO locationDTO);

    Location saveLocation(Location location);


    void deleteLocation(Long id);
}
