package br.com.dbccompany.fipebackend.service;

import br.com.dbccompany.fipebackend.dto.*;
import br.com.dbccompany.fipebackend.entity.*;

import java.time.LocalDateTime;
import java.util.List;

public interface CacheService {

  boolean isCacheStillValid(LocalDateTime generatedWhen);

  ManufacturerCache findValidManufacturerCache();

  void saveManufacturerCache(List<ManufacturerDto> result);

  ModelCache findValidModelCache(Integer manufacturerId, Integer vehicleId);

  void saveModelCache(Integer manufacturerId, Integer vehicleId, List<ModelDto> result);

  PriceCache findValidPriceCache(Integer manufacturerId, Integer vehicleId, String modelId);

  void savePriceCache(Integer manufacturerId, Integer vehicleId, String modelId, PriceDto price);

  PriceVariationCache findValidPriceVariationCache(Integer manufacturerId, Integer vehicleId);

  void savePriceVariationCache(
      Integer manufacturerId, Integer vehicleId, List<PriceVariationDto> result);

  VehicleCache findValidVehicleCache(Integer manufacturerId);

  void saveVehicleCache(Integer manufacturerId, List<VehicleDto> vehicles);
}
