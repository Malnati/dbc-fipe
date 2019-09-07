package br.com.dbccompany.fipebackend.service.impl;

import br.com.dbccompany.fipebackend.dto.*;
import br.com.dbccompany.fipebackend.service.ModelService;
import br.com.dbccompany.fipebackend.service.PriceService;
import br.com.dbccompany.fipebackend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleServiceImpl implements VehicleService {

  private final RestTemplate restTemplate;
  private final ModelService modelService;
  private final PriceService priceService;

  @Value("${fipe.vehicle.url.pattern}")
  private String serviceUrl;

  @Override
  public List<VehicleDto> listVehiclesByManufacturer(@NotNull Integer manufacturerId) {
    String url = String.format(serviceUrl, manufacturerId);
    ResponseEntity<List<VehicleDto>> response =
        restTemplate.exchange(
            url, HttpMethod.GET, null, new ParameterizedTypeReference<List<VehicleDto>>() {});
    List<VehicleDto> vehicles = response.getBody();
    vehicles.forEach(vehicleDto -> vehicleDto.setManufacturerId(manufacturerId));
    return vehicles;
  }

  @Override
  public List<PriceVariationDto> findPriceVariationByManufacturerAndVehicle(
      @NotNull Integer manufacturerId, @NotNull Integer vehicleId) {
    List<ModelDto> vehicleModels =
        this.modelService.listModelsByManufacturerAndVehicle(manufacturerId, vehicleId);
    List<PriceVariationDto> priceVariationDtos = this.groupVehiclesByModelAndYear(vehicleModels);
    this.calculateVariations(priceVariationDtos);
    return priceVariationDtos;
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
