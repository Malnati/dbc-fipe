package br.com.dbccompany.fipebackend.repository;

import br.com.dbccompany.fipebackend.entity.ManufacturerCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerCacheRepository extends CrudRepository<ManufacturerCache, String> {
}
