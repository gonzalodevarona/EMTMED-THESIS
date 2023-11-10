package com.emt.med.countingUnit;

import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.batch.BatchEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countingUnits")
public class CountingUnitEntityController {

    private CountingUnitEntityService countingUnitEntityService;

    public CountingUnitEntityController(CountingUnitEntityService countingUnitEntityService) {
        this.countingUnitEntityService = countingUnitEntityService;
    }

    @GetMapping
    public ResponseEntity<List<CountingUnitEntityDTO>> getAllCountingUnits() {
        return ResponseEntity.ok(countingUnitEntityService.getAllCountingUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountingUnitEntityDTO> getCountingUnitById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(countingUnitEntityService.getCountingUnitDTOById(id));
    }

    @PostMapping()
    public ResponseEntity<CountingUnitEntityDTO> addCountingUnit(@Valid @RequestBody CountingUnitEntityDTO countingUnitEntityDTO) throws RuntimeException{

        return new ResponseEntity<CountingUnitEntityDTO>(countingUnitEntityService.addCountingUnit(countingUnitEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<CountingUnitEntityDTO> updateCountingUnit(@Valid @RequestBody CountingUnitEntityDTO countingUnitEntityDTO) {
        return ResponseEntity.ok(countingUnitEntityService.updateCountingUnit(countingUnitEntityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCountingUnit(@PathVariable("id") Long id) {
        countingUnitEntityService.deleteCountingUnit(id);
        return ResponseEntity.ok("Erased counting unit with id "+ id);
    }
}
