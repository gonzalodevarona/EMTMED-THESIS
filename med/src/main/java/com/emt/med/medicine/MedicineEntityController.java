package com.emt.med.medicine;

import com.emt.med.consumable.ConsumableEntityDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicines")
public class MedicineEntityController {
    
    private MedicineEntityService medicineEntityService;

    public MedicineEntityController(MedicineEntityServiceImpl medicineEntityService) {
        this.medicineEntityService = medicineEntityService;
    }

    @GetMapping
    public ResponseEntity<List<MedicineEntityDTO>> getAllMedicines() {
        return ResponseEntity.ok(medicineEntityService.getAllMedicines());
    }

    @GetMapping("/inStock")
    public ResponseEntity<List<MedicineEntityDTO>> getAllMedicinesInStock() {
        return ResponseEntity.ok(medicineEntityService.getAllMedicinesInStock());
    }

    @GetMapping("/noBatches")
    public ResponseEntity<List<MedicineEntityDTO>> getAllMedicinesNoBatches() {
        return ResponseEntity.ok(medicineEntityService.getAllMedicinesNoBatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineEntityDTO> getMedicineEntityDTOById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(medicineEntityService.getMedicineEntityDTOById(id));
    }

    @PostMapping()
    public ResponseEntity<MedicineEntityDTO> addMedicine(@Valid @RequestBody MedicineEntityDTO medicineEntityDTO) throws RuntimeException{
        return new ResponseEntity<MedicineEntityDTO>(medicineEntityService.addMedicine(medicineEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<MedicineEntityDTO> updateMedicine(@Valid @RequestBody MedicineEntityDTO medicineEntityDTO) {
        return ResponseEntity.ok(medicineEntityService.updateMedicine(medicineEntityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMedicine(@PathVariable("id") Long id) {
        medicineEntityService.deleteMedicine(id);
        return ResponseEntity.ok("Erased medicine with id "+ id);
    }

    @DeleteMapping("/{id}/weightUnit")
    public ResponseEntity removeWeightUnitFromMedicine(@PathVariable("id") Long id) {
        return new ResponseEntity<MedicineEntityDTO>(medicineEntityService.removeWeightUnitFromMedicine(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/countingUnit")
    public ResponseEntity removeCountingUnitFromMedicine(@PathVariable("id") Long id) {
        return new ResponseEntity<MedicineEntityDTO>(medicineEntityService.removeCountingUnitFromMedicine(id), HttpStatus.OK);
    }

    @DeleteMapping("/{idMedicine}/medicationBatch/{idMedicationBatch}")
    public ResponseEntity removeMedicationBatchFromMedicine(@PathVariable("idMedicine") Long idMedicine, @PathVariable("idMedicationBatch") Long idMedicationBatch) {
        return new ResponseEntity<MedicineEntityDTO>(medicineEntityService.removeMedicationBatchFromMedicine(idMedicine, idMedicationBatch), HttpStatus.OK);
    }


}
