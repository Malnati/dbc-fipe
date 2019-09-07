package br.com.dbccompany.fipebackend.service.impl;

import br.com.dbccompany.fipebackend.dto.VehicleDto;
import br.com.dbccompany.fipebackend.entity.VehicleCache;
import br.com.dbccompany.fipebackend.service.CacheService;
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
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleServiceImpl implements VehicleService {

  private final CacheService cacheService;
  private final RestTemplate restTemplate;

  @Value("${fipe.url-pattern.vehicle}")
  private String serviceUrl;

  @Override
  public List<VehicleDto> listVehiclesByManufacturer(@NotNull Integer manufacturerId) {
    VehicleCache cache = cacheService.findValidVehicleCache(manufacturerId);
    if (cache != null) {
      return cache.getCachedResults();
    }
    List<VehicleDto> vehicles = fetchFromRestService(manufacturerId);
    cacheService.saveVehicleCache(manufacturerId, vehicles);
    return vehicles;
  }

  private List<VehicleDto> fetchFromRestService(Integer manufacturerId) {
    String url = String.format(serviceUrl, manufacturerId);
    ResponseEntity<List<VehicleDto>> response =
        restTemplate.exchange(
            url, HttpMethod.GET, null, new ParameterizedTypeReference<List<VehicleDto>>() {});
    List<VehicleDto> vehicles = response.getBody();
    vehicles.forEach(vehicleDto -> vehicleDto.setManufacturerId(manufacturerId));
    return vehicles;
  }
}
