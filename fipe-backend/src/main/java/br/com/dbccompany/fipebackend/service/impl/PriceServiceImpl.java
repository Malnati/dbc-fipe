package br.com.dbccompany.fipebackend.service.impl;

import br.com.dbccompany.fipebackend.dto.PriceDto;
import br.com.dbccompany.fipebackend.entity.PriceCache;
import br.com.dbccompany.fipebackend.service.CacheService;
import br.com.dbccompany.fipebackend.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceServiceImpl implements PriceService {

  private final RestTemplate restTemplate;
  private final Locale defaultLocale;
  private final CacheService cacheService;

  @Value("${fipe.url-pattern.price}")
  private String serviceUrl;

  public PriceDto getPriceManufacturerVehicleAndModel(
      @NotNull Integer manufacturerId, @NotNull Integer vehicleId, @NotNull String modelId) {
    PriceCache cache = cacheService.findValidPriceCache(manufacturerId, vehicleId, modelId);
    if (cache != null) {
      return cache.getCachedResults();
    }
    PriceDto price = fetchFromRestService(manufacturerId, vehicleId, modelId);
    cacheService.savePriceCache(manufacturerId, vehicleId, modelId, price);
    return price;
  }

  private PriceDto fetchFromRestService(
      @NotNull Integer manufacturerId, @NotNull Integer vehicleId, @NotNull String modelId) {
    String url = String.format(serviceUrl, manufacturerId, vehicleId, modelId);
    ResponseEntity<PriceDto> response = restTemplate.getForEntity(url, PriceDto.class);
    PriceDto price = response.getBody();
    this.extractRawPrice(price);
    return price;
  }

  private void extractRawPrice(PriceDto price) {
    NumberFormat numberFormat = NumberFormat.getNumberInstance(defaultLocale);
    try {
      String cleanValue = price.getPrice().replaceAll("[^\\d,.]", "").trim();
      Number rawValue = numberFormat.parse(cleanValue);
      price.setRawPrice(rawValue.doubleValue());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
