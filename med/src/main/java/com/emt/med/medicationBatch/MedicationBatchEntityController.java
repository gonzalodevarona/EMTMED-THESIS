package com.emt.med.medicationBatch;

import com.emt.med.medicine.MedicineEntityDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(medicationBatchEntityService.getMedicationBatchEntityDTOById(id));
    }

    @GetMapping("/{id}/medicine")
    public ResponseEntity<MedicineEntityDTO> getMedicineByMedicationBatchId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(medicationBatchEntityService.getMedicineByMedicationBatchId(id));
    }

    @PostMapping()
    public ResponseEntity<MedicationBatchEntityDTO> addMedicationBatch(@Valid @RequestBody MedicationBatchEntityDTO medicationBatchEntityDTO) {
        return new ResponseEntity<MedicationBatchEntityDTO>(medicationBatchEntityService.addMedicationBatch(medicationBatchEntityDTO), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteMedicationBatch(@PathVariable("id") Long id) {
        medicationBatchEntityService.deleteMedicationBatch(id);
        return ResponseEntity.ok("Erased medication batch with id "+ id);
    }

    @DeleteMapping("/{idMedicationBatch}/location/{idLocation}")
    public ResponseEntity removeLocationFromMedicine(@PathVariable("idMedicationBatch") Long idMedicationBatch, @PathVariable("idLocation") Long idLocation) {
        return new ResponseEntity<MedicationBatchEntityDTO>(medicationBatchEntityService.removeLocationFromMedicationBatch(idMedicationBatch, idLocation), HttpStatus.OK);
    }
}
