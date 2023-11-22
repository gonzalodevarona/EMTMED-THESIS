package com.emt.med.pharmacy;

import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pharmacyCategories")
public class PharmacyCategoryController {

    @GetMapping()
    public PharmacyCategory[] getAllPharmacyCategories() {
        return PharmacyCategory.values();
    }
}

