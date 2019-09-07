package br.com.dbccompany.fipebackend.service.impl;

import br.com.dbccompany.fipebackend.dto.PriceDto;
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

  @Value("${fipe.price.url.pattern}")
  private String serviceUrl;

  public PriceDto getPriceManufacturerVehicleAndModel(
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
      String cleanValue = price.getPrice().replaceAll("[^\\d,.]", "").strip();
      Number rawValue = numberFormat.parse(cleanValue);
      price.setRawPrice(rawValue.doubleValue());
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
