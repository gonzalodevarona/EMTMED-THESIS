package com.emt.med.value;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/values")
public class ValueEntityController {
    
    private ValueEntityService valueEntityService;

    public ValueEntityController(ValueEntityService valueEntityService) {
        this.valueEntityService = valueEntityService;
    }

    @GetMapping
    public ResponseEntity<List<ValueEntityDTO>> getAllValues() {
        return ResponseEntity.ok(valueEntityService.getAllValues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValueEntityDTO> getValueById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(valueEntityService.getValueEntityById(id));
    }

    @PostMapping()
    public ResponseEntity<ValueEntityDTO> addValue(@Valid @RequestBody ValueEntityDTO valueEntityDTO) throws RuntimeException{

        return new ResponseEntity<ValueEntityDTO>(valueEntityService.addValue(valueEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<ValueEntityDTO> updateValue(@Valid @RequestBody ValueEntityDTO valueEntityDTO) {
        return ResponseEntity.ok(valueEntityService.updateValue(valueEntityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteValue(@PathVariable("id") Long id) {
        valueEntityService.deleteValue(id);
        return ResponseEntity.ok("Erased value with id "+ id);
    }
}
