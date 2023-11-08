package com.emt.med.medicine;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicines")
public class MedicineEntityController {
    
    private MedicineEntityServiceImpl medicineEntityService;

    public MedicineEntityController(MedicineEntityServiceImpl medicineEntityService) {
        this.medicineEntityService = medicineEntityService;
    }

    @GetMapping
    public ResponseEntity<List<MedicineEntityDTO>> getAllMedicines() {
        return ResponseEntity.ok(medicineEntityService.getAllMedicines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineEntityDTO> getMedicineEntityDTOById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(medicineEntityService.getMedicineEntityDTOById(id));
    }

    @PostMapping()
    public ResponseEntity<MedicineEntityDTO> addMedicine(@Valid @RequestBody MedicineEntityDTO medicineEntityDTO) throws RuntimeException{

        return new ResponseEntity<MedicineEntityDTO>(medicineEntityService.addMedicine(medicineEntityDTO), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<MedicineEntityDTO> updateMedicine(@Valid @RequestBody MedicineEntityDTO medicineEntityDTO) {
        return ResponseEntity.ok(medicineEntityService.updateMedicine(medicineEntityDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteMedicine(@PathVariable("id") Long id) {
        medicineEntityService.deleteMedicine(id);
        return ResponseEntity.ok("Erased medicine with id "+ id);
    }
}
