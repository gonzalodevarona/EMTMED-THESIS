package com.emt.med.location;

import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/locations")
public class LocationController {

    private LocationRepository locationRepository;

    private LocationService locationService;

    static LocationMapper locationMapper = Mappers.getMapper(LocationMapper.class);

    public LocationController(LocationRepository locationRepository, LocationService locationService) {
        this.locationRepository = locationRepository;
        this.locationService = locationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(locationService.getLocationDTOById(id));
    }

    @PostMapping()
    public ResponseEntity<LocationDTO> addLocation(@Valid @RequestBody LocationDTO locationDTO) throws RuntimeException{
        return new ResponseEntity<LocationDTO>(locationMapper.toDTO(locationRepository.save(locationMapper.toEntity(locationDTO))), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteInventoryOrder(@PathVariable("id") Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok("Erased location with id "+ id);
    }
}
