package com.emt.med.inventoryOrder;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventoryOrders")
public class InventoryOrderEntityController {
    
    private InventoryOrderEntityService inventoryOrderEntityService;

    public InventoryOrderEntityController(InventoryOrderEntityService inventoryOrderEntityService) {
        this.inventoryOrderEntityService = inventoryOrderEntityService;
    }

    @GetMapping
    public ResponseEntity<List<InventoryOrderEntityDTO>> getAllInventoryOrders() {
        return ResponseEntity.ok(inventoryOrderEntityService.getAllInventoryOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryOrderEntityDTO> getInventoryOrderById(@PathVariable("id") Long id) {
        InventoryOrderEntityDTO x = inventoryOrderEntityService.getInventoryOrderEntityById(id);
        return ResponseEntity.ok(x);
    }

    @PostMapping()
    public ResponseEntity<InventoryOrderEntityDTO> addInventoryOrder(@Valid @RequestBody InventoryOrderEntityDTO inventoryOrderEntityDTO) throws RuntimeException{

        return new ResponseEntity<InventoryOrderEntityDTO>(inventoryOrderEntityService.addInventoryOrder(inventoryOrderEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<InventoryOrderEntityDTO> updateInventoryOrder(@Valid @RequestBody InventoryOrderEntityDTO inventoryOrderEntityDTO) {
        return ResponseEntity.ok(inventoryOrderEntityService.updateInventoryOrder(inventoryOrderEntityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteInventoryOrder(@PathVariable("id") Long id) {
        inventoryOrderEntityService.deleteInventoryOrder(id);
        return ResponseEntity.ok("Erased inventory order with id "+ id);
    }
}
