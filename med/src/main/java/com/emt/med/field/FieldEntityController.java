package com.emt.med.field;

import com.emt.med.value.ValueEntityDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fields")
public class FieldEntityController {
    
    private FieldEntityService fieldEntityService;

    public FieldEntityController(FieldEntityService fieldEntityService) {
        this.fieldEntityService = fieldEntityService;
    }

    @GetMapping
    public ResponseEntity<List<FieldEntityDTO>> getAllFields() {
        return ResponseEntity.ok(fieldEntityService.getAllFields());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldEntityDTO> getFieldById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fieldEntityService.getFieldEntityById(id));
    }

    @PostMapping()
    public ResponseEntity<FieldEntityDTO> addField(@Valid @RequestBody FieldEntityDTO fieldEntityDTO) throws RuntimeException{

        return new ResponseEntity<FieldEntityDTO>(fieldEntityService.addField(fieldEntityDTO), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/values")
    public ResponseEntity<FieldEntityDTO> addValueToFieldById(@PathVariable("id") Long id, @Valid @RequestBody ValueEntityDTO valueEntityDTO) throws RuntimeException{

        return new ResponseEntity<FieldEntityDTO>(fieldEntityService.addValueToFieldById(id, valueEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<FieldEntityDTO> updateField(@Valid @RequestBody FieldEntityDTO fieldEntityDTO) {
        return ResponseEntity.ok(fieldEntityService.updateField(fieldEntityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteField(@PathVariable("id") Long id) {
        fieldEntityService.deleteField(id);
        return ResponseEntity.ok("Erased field with id "+ id);
    }
}
