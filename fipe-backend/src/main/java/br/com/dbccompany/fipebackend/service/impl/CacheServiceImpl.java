package br.com.dbccompany.fipebackend.service.impl;

import br.com.dbccompany.fipebackend.dto.*;
import br.com.dbccompany.fipebackend.entity.*;
import br.com.dbccompany.fipebackend.repository.*;
import br.com.dbccompany.fipebackend.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CacheServiceImpl implements CacheService {

  @Value("${app.cache-ttl}")
  private Long defaultCacheTtl;

  private final ManufacturerCacheRepository manufacturerCacheRepository;
  private final ModelCacheRepository modelCacheRepository;
  private final PriceCacheRepository priceCacheRepository;
  private final PriceVariationCacheRepository priceVariationCacheRepository;
  private final VehicleCacheRepository vehicleCacheRepository;

  @Override
  public boolean isCacheStillValid(LocalDateTime generatedWhen) {
    final LocalDateTime expirationTime = generatedWhen.plusMinutes(defaultCacheTtl);
    return expirationTime.isAfter(LocalDateTime.now());
  }

  @Override
  public ManufacturerCache findValidManufacturerCache() {
    Iterable<ManufacturerCache> caches = manufacturerCacheRepository.findAll();
    if (caches == null) return null;
    for (ManufacturerCache cache : caches) {
      if (this.isCacheStillValid(cache.getGeneratedWhen())) return cache;
    }
    return null;
  }

  @Override
  public void saveManufacturerCache(List<ManufacturerDto> result) {
    ManufacturerCache cache =
        ManufacturerCache.builder()
            .cachedResults(result)
            .generatedWhen(LocalDateTime.now())
            .build();
    manufacturerCacheRepository.save(cache);
  }

  @Override
  public ModelCache findValidModelCache(Integer manufacturerId, Integer vehicleId) {
    Iterable<ModelCache> caches =
        modelCacheRepository.findByManufacturerIdAndVehicleId(manufacturerId, vehicleId);
    if (caches == null) return null;
    for (ModelCache cache : caches) {
      if (this.isCacheStillValid(cache.getGeneratedWhen())) return cache;
    }
    return null;
  }

  @Override
  public void saveModelCache(Integer manufacturerId, Integer vehicleId, List<ModelDto> result) {
    ModelCache cache =
        ModelCache.builder()
            .manufacturerId(manufacturerId)
            .vehicleId(vehicleId)
            .cachedResults(result)
            .generatedWhen(LocalDateTime.now())
            .build();
    modelCacheRepository.save(cache);
  }

  @Override
  public PriceCache findValidPriceCache(Integer manufacturerId, Integer vehicleId, String modelId) {
    Iterable<PriceCache> caches =
        priceCacheRepository.findByManufacturerIdAndVehicleIdAndModelId(
            manufacturerId, vehicleId, modelId);
    if (caches == null) return null;
    for (PriceCache cache : caches) {
      if (this.isCacheStillValid(cache.getGeneratedWhen())) return cache;
    }
    return null;
  }

  @Override
  public void savePriceCache(
      Integer manufacturerId, Integer vehicleId, String modelId, PriceDto price) {
    PriceCache cache =
        PriceCache.builder()
            .manufacturerId(manufacturerId)
            .vehicleId(vehicleId)
            .modelId(modelId)
            .cachedResults(price)
            .generatedWhen(LocalDateTime.now())
            .build();
    priceCacheRepository.save(cache);
  }

  @Override
  public PriceVariationCache findValidPriceVariationCache(
      Integer manufacturerId, Integer vehicleId) {
    Iterable<PriceVariationCache> caches =
        priceVariationCacheRepository.findByManufacturerIdAndVehicleId(manufacturerId, vehicleId);
    if (caches == null) return null;
    for (PriceVariationCache cache : caches) {
      if (this.isCacheStillValid(cache.getGeneratedWhen())) return cache;
    }
    return null;
  }

  @Override
  public void savePriceVariationCache(
      Integer manufacturerId, Integer vehicleId, List<PriceVariationDto> result) {
    PriceVariationCache cache =
        PriceVariationCache.builder()
            .cachedResults(result)
            .generatedWhen(LocalDateTime.now())
            .manufacturerId(manufacturerId)
            .vehicleId(vehicleId)
            .build();
    priceVariationCacheRepository.save(cache);
  }

  @Override
  public VehicleCache findValidVehicleCache(Integer manufacturerId) {
    Iterable<VehicleCache> caches = vehicleCacheRepository.findByManufacturerId(manufacturerId);
    if (caches == null) return null;
    for (VehicleCache cache : caches) {
      if (this.isCacheStillValid(cache.getGeneratedWhen())) return cache;
    }
    return null;
  }

  @Override
  public void saveVehicleCache(Integer manufacturerId, List<VehicleDto> vehicles) {
    VehicleCache cache =
        VehicleCache.builder()
            .cachedResults(vehicles)
            .generatedWhen(LocalDateTime.now())
            .manufacturerId(manufacturerId)
            .build();
    vehicleCacheRepository.save(cache);
  }
}
