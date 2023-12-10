package com.emt.med.value;

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
        return ResponseEntity.ok(valueEntityService.getValueEntityDTOById(id));
    }

}
