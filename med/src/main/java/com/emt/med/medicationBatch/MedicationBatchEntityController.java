package com.emt.med.medicationBatch;

import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.batch.BatchEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicationBatches")
public class MedicationBatchEntityController {


    private MedicationBatchEntityService medicationBatchEntityService;

    public MedicationBatchEntityController(MedicationBatchEntityService medicationBatchEntityService) {
        this.medicationBatchEntityService = medicationBatchEntityService;
    }

    @GetMapping
    public ResponseEntity<List<MedicationBatchEntityDTO>> getAllMedicationBatches() {
        return ResponseEntity.ok(medicationBatchEntityService.getAllMedicationBatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationBatchEntityDTO> getMedicationBatchById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(medicationBatchEntityService.getMedicationBatchEntityById(id));
    }

    @PostMapping()
    public ResponseEntity<MedicationBatchEntityDTO> addMedicationBatch(@Valid @RequestBody MedicationBatchEntityDTO medicationBatchEntityDTO) {
        return new ResponseEntity<MedicationBatchEntityDTO>(medicationBatchEntityService.addMedicationBatch(medicationBatchEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<MedicationBatchEntityDTO> updateMedicationBatch(@Valid @RequestBody MedicationBatchEntityDTO medicationBatchEntityDTO) {
        return ResponseEntity.ok(medicationBatchEntityService.updateMedicationBatch(medicationBatchEntityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMedicationBatch(@PathVariable("id") Long id) {
        medicationBatchEntityService.deleteMedicationBatch(id);
        return ResponseEntity.ok("Erased medication batch with id "+ id);
    }
}
