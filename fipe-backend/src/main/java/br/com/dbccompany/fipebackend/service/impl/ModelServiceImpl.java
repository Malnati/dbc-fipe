package br.com.dbccompany.fipebackend.service.impl;

import br.com.dbccompany.fipebackend.dto.ModelDto;
import br.com.dbccompany.fipebackend.entity.ModelCache;
import br.com.dbccompany.fipebackend.service.CacheService;
import br.com.dbccompany.fipebackend.service.ModelService;
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
public class ModelServiceImpl implements ModelService {

  private final RestTemplate restTemplate;
  private final CacheService cacheService;

  @Value("${fipe.url-pattern.model}")
  private String serviceUrl;

  public List<ModelDto> listModelsByManufacturerAndVehicle(
      @NotNull Integer manufacturerId, @NotNull Integer vehicleId) {
    ModelCache cache = cacheService.findValidModelCache(manufacturerId, vehicleId);
    if (cache != null) {
      return cache.getCachedResults();
    }

    List<ModelDto> models = fetchFromRestService(manufacturerId, vehicleId);
    cacheService.saveModelCache(manufacturerId, vehicleId, models);
    return models;
  }

  private List<ModelDto> fetchFromRestService(
      @NotNull Integer manufacturerId, @NotNull Integer vehicleId) {
    String url = String.format(serviceUrl, manufacturerId, vehicleId);
    ResponseEntity<List<ModelDto>> response =
        restTemplate.exchange(
            url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ModelDto>>() {});
    List<ModelDto> models = response.getBody();
    models.forEach(
        modelDto -> {
          modelDto.setManufacturerId(manufacturerId);
          modelDto.setVehicleId(vehicleId);
        });
    return models;
  }
}
