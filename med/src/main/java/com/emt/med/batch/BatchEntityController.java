package com.emt.med.batch;

import com.emt.med.consumable.ConsumableEntityDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/batches")
public class BatchEntityController {

    private BatchEntityService batchEntityService;

    public BatchEntityController(BatchEntityService batchEntityService) {
        this.batchEntityService = batchEntityService;
    }

    @GetMapping
    public ResponseEntity<List<BatchEntityDTO>> getAllBatches() {
        return ResponseEntity.ok(batchEntityService.getAllBatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BatchEntityDTO> getBatchById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(batchEntityService.getBatchEntityDTOById(id));
    }

    @GetMapping("/{id}/consumable")
    public ResponseEntity<ConsumableEntityDTO> getConsumableByBatchId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(batchEntityService.getConsumableByBatchId(id));
    }

    @PostMapping()
    public ResponseEntity<BatchEntityDTO> addBatch(@Valid @RequestBody BatchEntityDTO batchEntityDTO) throws RuntimeException{

        return new ResponseEntity<BatchEntityDTO>(batchEntityService.addBatch(batchEntityDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBatch(@PathVariable("id") Long id) {
        batchEntityService.deleteBatch(id);
        return ResponseEntity.ok("Erased batch with id "+ id);
    }
}


