package com.emt.med.medicationBatchRequest;

import com.emt.med.weightUnit.WeightUnitEntityDTO;
import com.emt.med.weightUnit.WeightUnitEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicationBatchRequests")
public class MedicationBatchRequestEntityController {

    private MedicationBatchRequestEntityService medicationBatchRequestEntityService;

    public MedicationBatchRequestEntityController(MedicationBatchRequestEntityService medicationBatchRequestEntityService) {
        this.medicationBatchRequestEntityService = medicationBatchRequestEntityService;
    }

    @GetMapping
    public ResponseEntity<List<MedicationBatchRequestEntityDTO>> getAllMedicationBatchRequests() {
        return ResponseEntity.ok(medicationBatchRequestEntityService.getAllMedicationBatchRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationBatchRequestEntityDTO> getMedicationBatchRequestDTOtById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(medicationBatchRequestEntityService.getMedicationBatchRequestDTOtById(id));
    }

    @PostMapping()
    public ResponseEntity<MedicationBatchRequestEntityDTO> addMedicationBatchRequest(@Valid @RequestBody MedicationBatchRequestEntityDTO medicationBatchRequestEntityDTO) throws RuntimeException{
        return new ResponseEntity<MedicationBatchRequestEntityDTO>(medicationBatchRequestEntityService.addMedicationBatchRequest(medicationBatchRequestEntityDTO), HttpStatus.CREATED);
    }

}
