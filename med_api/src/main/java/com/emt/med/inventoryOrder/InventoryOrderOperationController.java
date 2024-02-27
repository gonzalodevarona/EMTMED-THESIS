package com.emt.med.inventoryOrder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventoryOrderOperations")
public class InventoryOrderOperationController {

    @GetMapping()
    public InventoryOrderOperation[] getAllInventoryOrderOperations() {
        return InventoryOrderOperation.values();
    }
}

