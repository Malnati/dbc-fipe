package br.com.dbccompany.fipebackend.entity;

import br.com.dbccompany.fipebackend.dto.ModelDto;
import br.com.dbccompany.fipebackend.dto.PriceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("price_cache")
public class PriceCache {
    @Id
    private String id;
    private Integer manufacturerId;
    private Integer vehicleId;
    private String modelId;
    private LocalDateTime generatedWhen;
    private PriceDto cachedResults;
}
