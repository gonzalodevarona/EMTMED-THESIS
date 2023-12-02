package com.emt.med.baseBatch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batchStatuses")
public class BatchStatusController {

    @GetMapping()
    public BatchStatus[] getAllBatchStatuses() {
        return BatchStatus.values();
    }
}

