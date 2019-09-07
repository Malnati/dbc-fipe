package br.com.dbccompany.fipebackend.service.impl;

import br.com.dbccompany.fipebackend.dto.ModelDto;
import br.com.dbccompany.fipebackend.dto.PriceDto;
import br.com.dbccompany.fipebackend.dto.PricePerYearDto;
import br.com.dbccompany.fipebackend.dto.PriceVariationDto;
import br.com.dbccompany.fipebackend.entity.PriceVariationCache;
import br.com.dbccompany.fipebackend.repository.PriceVariationCacheRepository;
import br.com.dbccompany.fipebackend.service.CacheService;
import br.com.dbccompany.fipebackend.service.ModelService;
import br.com.dbccompany.fipebackend.service.PriceService;
import br.com.dbccompany.fipebackend.service.PriceVariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceVariationServiceImpl implements PriceVariationService {

  private final PriceVariationCacheRepository priceVariationCacheRepository;
  private final CacheService cacheService;
  private final RestTemplate restTemplate;
  private final ModelService modelService;
  private final PriceService priceService;

  @Value("${fipe.url-pattern.vehicle}")
  private String serviceUrl;

  @Override
  public List<PriceVariationDto> findPriceVariationByManufacturerAndVehicle(
      Integer manufacturerId, Integer vehicleId) {
    PriceVariationCache cache = this.findValidCache(manufacturerId, vehicleId);
    if (cache != null) {
      return cache.getCachedResults();
    }
    List<PriceVariationDto> result =
        this.calculatePriceVariationsForVehicle(manufacturerId, vehicleId);
    this.saveCache(manufacturerId, vehicleId, result);
    return result;
  }

  private List<PriceVariationDto> calculatePriceVariationsForVehicle(
      Integer manufacturerId, Integer vehicleId) {
    List<ModelDto> vehicleModels =
        this.modelService.listModelsByManufacturerAndVehicle(manufacturerId, vehicleId);
    List<PriceVariationDto> priceVariationDtos = this.groupVehiclesByModelAndYear(vehicleModels);
    this.calculateVariations(priceVariationDtos);
    return priceVariationDtos;
  }

  private PriceVariationCache findValidCache(Integer manufacturerId, Integer vehicleId) {
    Iterable<PriceVariationCache> caches =
        priceVariationCacheRepository.findByManufacturerIdAndVehicleId(manufacturerId, vehicleId);
    if (caches == null) return null;
    for (PriceVariationCache cache : caches) {
      if (cacheService.isCacheStillValid(cache.getGeneratedWhen())) return cache;
    }
    return null;
  }

  private void saveCache(
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

  private void calculateVariations(List<PriceVariationDto> priceVariationDtos) {
    priceVariationDtos.forEach(
        priceVariationDto -> {
          BigDecimal lastPrice = null;
          for (PricePerYearDto pricePerYearDto : priceVariationDto.getPrices()) {
            if (lastPrice == null) {
              lastPrice = pricePerYearDto.getValue();
              pricePerYearDto.setVariation(BigDecimal.ZERO);
              continue;
            }
            pricePerYearDto.setVariation(
                pricePerYearDto
                    .getValue()
                    .divide(lastPrice, 2, RoundingMode.HALF_UP)
                    .subtract(BigDecimal.ONE)
                    .multiply(BigDecimal.valueOf(100)));
            lastPrice = pricePerYearDto.getValue();
          }
        });
  }

  private List<PriceVariationDto> groupVehiclesByModelAndYear(List<ModelDto> vehicleModels) {
    List<PriceVariationDto> prices = new ArrayList<>();
    vehicleModels.forEach(
        modelDto -> {
          PriceVariationDto price = new PriceVariationDto(modelDto.getVehicle());
          if (prices.contains(price)) {
            price = prices.get(prices.indexOf(price));
          } else {
            prices.add(price);
          }
          PriceDto priceDto =
              priceService.getPriceManufacturerVehicleAndModel(
                  modelDto.getManufacturerId(), modelDto.getVehicleId(), modelDto.getId());
          price
              .getPrices()
              .add(
                  PricePerYearDto.builder()
                      .year(Integer.valueOf(priceDto.getYearOfModel()))
                      .value(BigDecimal.valueOf(priceDto.getRawPrice()))
                      .build());
        });
    return prices;
  }
}
