package com.emt.med.weightUnit;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weightUnits")
public class WeightUnitEntityController {

    private WeightUnitEntityService weightUnitEntityService;

    public WeightUnitEntityController(WeightUnitEntityService weightUnitEntityService) {
        this.weightUnitEntityService = weightUnitEntityService;
    }

    @GetMapping
    public ResponseEntity<List<WeightUnitEntityDTO>> getAllWeightUnits() {
        return ResponseEntity.ok(weightUnitEntityService.getAllWeightUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeightUnitEntityDTO> getWeightUnitById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(weightUnitEntityService.getWeightUniDTOtById(id));
    }

    @PostMapping()
    public ResponseEntity<WeightUnitEntityDTO> addWeightUnit(@Valid @RequestBody WeightUnitEntityDTO weightUnitEntityDTO) throws RuntimeException{
        return new ResponseEntity<WeightUnitEntityDTO>(weightUnitEntityService.addWeightUnit(weightUnitEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<WeightUnitEntityDTO> updateWeightUnit(@Valid @RequestBody WeightUnitEntityDTO weightUnitEntityDTO) {
        return ResponseEntity.ok(weightUnitEntityService.updateWeightUnit(weightUnitEntityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteWeightUnit(@PathVariable("id") Long id) {
        weightUnitEntityService.deleteWeightUnit(id);
        return ResponseEntity.ok("Erased weight unit with id "+ id);
    }
}
