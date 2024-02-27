package com.emt.med.batchRequest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batchRequests")
public class BatchRequestEntityController {

    private BatchRequestEntityService batchRequestEntityService;

    public BatchRequestEntityController(BatchRequestEntityService batchRequestEntityService) {
        this.batchRequestEntityService = batchRequestEntityService;
    }

    @GetMapping
    public ResponseEntity<List<BatchRequestEntityDTO>> getAllMedicationBatchRequests() {
        return ResponseEntity.ok(batchRequestEntityService.getAllBatchRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchRequestEntityDTO> getMedicationBatchRequestDTOtById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(batchRequestEntityService.getBatchRequestDTOtById(id));
    }

    @PostMapping()
    public ResponseEntity<BatchRequestEntityDTO> addMedicationBatchRequest(@Valid @RequestBody BatchRequestEntityDTO batchRequestEntityDTO) throws RuntimeException{
        return new ResponseEntity<BatchRequestEntityDTO>(batchRequestEntityService.addBatchRequest(batchRequestEntityDTO), HttpStatus.CREATED);
    }

}
