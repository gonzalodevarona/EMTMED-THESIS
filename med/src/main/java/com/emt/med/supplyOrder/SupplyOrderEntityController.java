package com.emt.med.supplyOrder;

import com.emt.med.order.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplyOrders")
public class SupplyOrderEntityController {
    
    private SupplyOrderEntityService supplyOrderEntityService;

    public SupplyOrderEntityController(SupplyOrderEntityService supplyOrderEntityService) {
        this.supplyOrderEntityService = supplyOrderEntityService;
    }

    @GetMapping
    public ResponseEntity<List<SupplyOrderEntityDTO>> getAllSupplyOrders() {
        return ResponseEntity.ok(supplyOrderEntityService.getAllSupplyOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplyOrderEntityDTO> getSupplyOrderEntityById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(supplyOrderEntityService.getSupplyOrderEntityById(id));
    }

    @PostMapping()
    public ResponseEntity<SupplyOrderEntityDTO> addSupplyOrder(@Valid @RequestBody SupplyOrderEntityDTO supplyOrderEntityDTO) throws RuntimeException{

        return new ResponseEntity<SupplyOrderEntityDTO>(supplyOrderEntityService.addSupplyOrder(supplyOrderEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{newStatus}")
    public ResponseEntity<SupplyOrderEntityDTO> changeSupplyOrderStatus(@PathVariable("id") Long id, @PathVariable("newStatus") OrderStatus newStatus) {
        return ResponseEntity.ok(supplyOrderEntityService.changeSupplyOrderStatus(id, newStatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSupplyOrder(@PathVariable("id") Long id) {
        supplyOrderEntityService.deleteSupplyOrder(id);
        return ResponseEntity.ok("Erased supply order with id "+ id);
    }
}
