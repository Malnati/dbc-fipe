package br.com.dbccompany.fipebackend.repository;

import br.com.dbccompany.fipebackend.entity.VehicleCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleCacheRepository extends CrudRepository<VehicleCache, String> {

    Iterable<VehicleCache> findByManufacturerId(Integer manufacturerId);
}
