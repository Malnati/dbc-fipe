package br.com.dbccompany.fipebackend.entity;

import br.com.dbccompany.fipebackend.dto.ManufacturerDto;
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
@Document("manufacturer_cache")
public class ManufacturerCache {
    @Id
    private String id;
    private LocalDateTime generatedWhen;
    private List<ManufacturerDto> cachedResults;
}
