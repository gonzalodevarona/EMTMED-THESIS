package com.emt.med.consumable;

import com.emt.med.medicine.MedicineEntityDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumables")
public class ConsumableEntityController {
    
    private ConsumableEntityService consumableEntityService;

    public ConsumableEntityController(ConsumableEntityService consumableEntityService) {
        this.consumableEntityService = consumableEntityService;
    }

    @GetMapping
    public ResponseEntity<List<ConsumableEntityDTO>> getAllConsumables() {
        return ResponseEntity.ok(consumableEntityService.getAllConsumables());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumableEntityDTO> getConsumableById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(consumableEntityService.getConsumableEntityDTOById(id));
    }

    @PostMapping()
    public ResponseEntity<ConsumableEntityDTO> addConsumable(@Valid @RequestBody ConsumableEntityDTO consumableEntityDTO) throws RuntimeException{

        return new ResponseEntity<ConsumableEntityDTO>(consumableEntityService.addConsumable(consumableEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<ConsumableEntityDTO> updateConsumable(@Valid @RequestBody ConsumableEntityDTO consumableEntityDTO) {
        return ResponseEntity.ok(consumableEntityService.updateConsumable(consumableEntityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteConsumable(@PathVariable("id") Long id) {
        consumableEntityService.deleteConsumable(id);
        return ResponseEntity.ok("Erased consumable with id "+ id);
    }

    @DeleteMapping("/{id}/weightUnit")
    public ResponseEntity removeWeightUnitFromConsumable(@PathVariable("id") Long id) {
        return new ResponseEntity<ConsumableEntityDTO>(consumableEntityService.removeWeightUnitFromConsumable(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/countingUnit")
    public ResponseEntity removeCountingUnitFromConsumable(@PathVariable("id") Long id) {
        return new ResponseEntity<ConsumableEntityDTO>(consumableEntityService.removeCountingUnitFromConsumable(id), HttpStatus.OK);
    }

    @DeleteMapping("/{idConsumable}/batch/{idBatch}")
    public ResponseEntity removeBatchFromConsumable(@PathVariable("idConsumable") Long idConsumable, @PathVariable("idBatch") Long idBatch) {
        return new ResponseEntity<ConsumableEntityDTO>(consumableEntityService.removeBatchFromConsumable(idConsumable, idBatch), HttpStatus.OK);
    }
}
