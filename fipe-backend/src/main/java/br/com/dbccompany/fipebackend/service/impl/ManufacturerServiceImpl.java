package br.com.dbccompany.fipebackend.service.impl;

import br.com.dbccompany.fipebackend.dto.ManufacturerDto;
import br.com.dbccompany.fipebackend.entity.ManufacturerCache;
import br.com.dbccompany.fipebackend.service.CacheService;
import br.com.dbccompany.fipebackend.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ManufacturerServiceImpl implements ManufacturerService {

  private final RestTemplate restTemplate;
  private final CacheService cacheService;

  @Value("${fipe.url-pattern.manufacturer}")
  private String serviceUrl;

  @Override
  public List<ManufacturerDto> listManufacturers() {
    ManufacturerCache cache = cacheService.findValidManufacturerCache();
    if (cache != null) {
      return cache.getCachedResults();
    }

    List<ManufacturerDto> result = fetchFromRestService();
    cacheService.saveManufacturerCache(result);
    return result;
  }

  private List<ManufacturerDto> fetchFromRestService() {
    ResponseEntity<List<ManufacturerDto>> response =
        restTemplate.exchange(
            serviceUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<ManufacturerDto>>() {});
    return response.getBody();
  }
}
