package com.emt.med.disposalStation;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disposalStations")
public class DisposalStationEntityController {
    
    private DisposalStationEntityService disposalStationEntityService;

    public DisposalStationEntityController(DisposalStationEntityService disposalStationEntityService) {
        this.disposalStationEntityService = disposalStationEntityService;
    }

    @GetMapping
    public ResponseEntity<List<DisposalStationEntityDTO>> getAllDisposalStations() {
        return ResponseEntity.ok(disposalStationEntityService.getAllDisposalStations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisposalStationEntityDTO> getDisposalStationEntityDTOById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(disposalStationEntityService.getDisposalStationEntityDTOById(id));
    }

    @PostMapping()
    public ResponseEntity<DisposalStationEntityDTO> addDisposalStation(@Valid @RequestBody DisposalStationEntityDTO consumableEntityDTO) throws RuntimeException{
        return new ResponseEntity<DisposalStationEntityDTO>(disposalStationEntityService.addDisposalStation(consumableEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<DisposalStationEntityDTO> updateDisposalStation(@Valid @RequestBody DisposalStationEntityDTO consumableEntityDTO) {
        return ResponseEntity.ok(disposalStationEntityService.updateDisposalStation(consumableEntityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDisposalStation(@PathVariable("id") Long id) {
        disposalStationEntityService.deleteDisposalStation(id);
        return ResponseEntity.ok("Erased disposal station with id "+ id);
    }
}
