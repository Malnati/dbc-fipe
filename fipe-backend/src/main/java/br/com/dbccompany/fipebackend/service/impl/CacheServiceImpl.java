package br.com.dbccompany.fipebackend.service.impl;

import br.com.dbccompany.fipebackend.service.CacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CacheServiceImpl implements CacheService {

  @Value("${app.cache-ttl}")
  private Long defaultCacheTtl;

  @Override
  public boolean isCacheStillValid(LocalDateTime generatedWhen) {
    final LocalDateTime expirationTime = generatedWhen.plusMinutes(defaultCacheTtl);
    return expirationTime.isAfter(LocalDateTime.now());
  }
}
