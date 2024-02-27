package com.emt.med.pharmacy;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacies")
public class PharmacyEntityController {
    
    private PharmacyEntityService pharmacyEntityService;

    public PharmacyEntityController(PharmacyEntityService pharmacyEntityService) {
        this.pharmacyEntityService = pharmacyEntityService;
    }

    @GetMapping
    public ResponseEntity<List<PharmacyEntityDTO>> getAllPharmacies() {
        return ResponseEntity.ok(pharmacyEntityService.getAllPharmacies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PharmacyEntityDTO> getPharmacyEntityDTOById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pharmacyEntityService.getPharmacyEntityDTOById(id));
    }

    @PostMapping()
    public ResponseEntity<PharmacyEntityDTO> addPharmacy(@Valid @RequestBody PharmacyEntityDTO medicineEntityDTO) throws RuntimeException{

        return new ResponseEntity<PharmacyEntityDTO>(pharmacyEntityService.addPharmacy(medicineEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<PharmacyEntityDTO> updatePharmacy(@Valid @RequestBody PharmacyEntityDTO medicineEntityDTO) {
        return ResponseEntity.ok(pharmacyEntityService.updatePharmacy(medicineEntityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePharmacy(@PathVariable("id") Long id) {
        pharmacyEntityService.deletePharmacy(id);
        return ResponseEntity.ok("Erased pharmacy with id "+ id);
    }
}
