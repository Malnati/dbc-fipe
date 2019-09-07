package br.com.dbccompany.fipebackend.service.impl;

import br.com.dbccompany.fipebackend.dto.ManufacturerDto;
import br.com.dbccompany.fipebackend.entity.ManufacturerCache;
import br.com.dbccompany.fipebackend.repository.ManufacturerCacheRepository;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ManufacturerServiceImpl implements ManufacturerService {

  private final RestTemplate restTemplate;
  private final ManufacturerCacheRepository manufacturerCacheRepository;
  private final CacheService cacheService;

  @Value("${fipe.url-pattern.manufacturer}")
  private String serviceUrl;

  @Override
  public List<ManufacturerDto> listManufacturers() {
    ManufacturerCache cache = this.findValidCache();
    if (cache != null) {
      return cache.getCachedResults();
    }

    List<ManufacturerDto> result = fetchFromRestService();
    saveCache(result);
    return result;
  }

  private void saveCache(List<ManufacturerDto> result) {
    ManufacturerCache cache =
        ManufacturerCache.builder()
            .cachedResults(result)
            .generatedWhen(LocalDateTime.now())
            .build();
    manufacturerCacheRepository.save(cache);
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

  private ManufacturerCache findValidCache() {
    Iterable<ManufacturerCache> caches = manufacturerCacheRepository.findAll();
    if (caches == null) return null;
    for (ManufacturerCache cache : caches) {
      if (cacheService.isCacheStillValid(cache.getGeneratedWhen())) return cache;
    }
    return null;
  }
}
