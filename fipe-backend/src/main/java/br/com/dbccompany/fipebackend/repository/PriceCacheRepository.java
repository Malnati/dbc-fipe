package br.com.dbccompany.fipebackend.repository;

import br.com.dbccompany.fipebackend.entity.PriceCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceCacheRepository extends CrudRepository<PriceCache, String> {

  Iterable<PriceCache> findByManufacturerIdAndVehicleIdAndModelId(
      Integer manufacturerId, Integer vehicleId, String modelId);
}
