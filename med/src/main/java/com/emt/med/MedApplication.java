package com.emt.med;

import com.emt.med.countingUnit.CountingUnitEntityDTO;
import com.emt.med.countingUnit.CountingUnitEntityService;
import com.emt.med.pharmacy.PharmacyCategory;
import com.emt.med.pharmacy.PharmacyEntity;
import com.emt.med.pharmacy.PharmacyEntityDTO;
import com.emt.med.pharmacy.PharmacyEntityService;
import com.emt.med.weightUnit.WeightUnitEntityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MedApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner (PharmacyEntityService pharmacyEntityService, CountingUnitEntityService countingUnitEntityService, WeightUnitEntityService weightUnitEntityService) {

		return (args) -> {

			PharmacyEntity existingWarehouse = pharmacyEntityService.getPharmacyByCategory(PharmacyCategory.WAREHOUSE);
			PharmacyEntity existingPrincipal = pharmacyEntityService.getPharmacyByCategory(PharmacyCategory.PRINCIPAL);
			if(existingWarehouse == null){
				PharmacyEntityDTO newWarehouse = new PharmacyEntityDTO();
				newWarehouse.setCategory(PharmacyCategory.WAREHOUSE);
				newWarehouse.setName("Bodega");
				pharmacyEntityService.addPharmacy(newWarehouse);
			}
			if(existingPrincipal == null){
				PharmacyEntityDTO newWarehouse = new PharmacyEntityDTO();
				newWarehouse.setCategory(PharmacyCategory.PRINCIPAL);
				newWarehouse.setName("Farmacia principal");
				pharmacyEntityService.addPharmacy(newWarehouse);
			}

		};

	}

}
