package br.com.dbccompany.fipebackend.entity;

import br.com.dbccompany.fipebackend.dto.PriceVariationDto;
import br.com.dbccompany.fipebackend.dto.VehicleDto;
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
@Document("price_variation_cache")
public class PriceVariationCache {
    @Id
    private String id;
    private Integer manufacturerId;
    private Integer vehicleId;
    private LocalDateTime generatedWhen;
    private List<PriceVariationDto> cachedResults;
}
