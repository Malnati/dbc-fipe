package br.com.dbccompany.fipebackend.repository;

import br.com.dbccompany.fipebackend.entity.PriceVariationCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceVariationCacheRepository extends CrudRepository<PriceVariationCache, String> {

    Iterable<PriceVariationCache> findByManufacturerIdAndVehicleId(Integer manufacturerId, Integer vehicleId);
}
