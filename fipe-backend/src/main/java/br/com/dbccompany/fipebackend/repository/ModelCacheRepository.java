package br.com.dbccompany.fipebackend.repository;

import br.com.dbccompany.fipebackend.entity.ModelCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelCacheRepository extends CrudRepository<ModelCache, String> {

    Iterable<ModelCache> findByManufacturerIdAndVehicleId(Integer manufacturerId, Integer vehicleId);
}
