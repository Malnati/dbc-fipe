package br.com.dbccompany.fipebackend.entity;

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
@Document("vehicle_cache")
public class VehicleCache {
    @Id
    private String id;
    private Integer manufacturerId;
    private LocalDateTime generatedWhen;
    private List<VehicleDto> cachedResults;
}
