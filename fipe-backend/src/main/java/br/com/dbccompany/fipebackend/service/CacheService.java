package br.com.dbccompany.fipebackend.service;

import java.time.LocalDateTime;

public interface CacheService {

    boolean isCacheStillValid(LocalDateTime generatedWhen);
}
