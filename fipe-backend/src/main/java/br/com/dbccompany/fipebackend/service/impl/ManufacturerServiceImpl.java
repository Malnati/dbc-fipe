package br.com.dbccompany.fipebackend.service.impl;

import br.com.dbccompany.fipebackend.dto.ManufacturerDto;
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

  @Value("${fipe.manufacturer.url.pattern}")
  private String serviceUrl;

  @Override
  public List<ManufacturerDto> listManufacturers() {
    ResponseEntity<List<ManufacturerDto>> response =
        restTemplate.exchange(
            serviceUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<ManufacturerDto>>() {});
    return response.getBody();
  }
}
