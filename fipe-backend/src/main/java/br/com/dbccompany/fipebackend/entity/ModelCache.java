package br.com.dbccompany.fipebackend.entity;

import br.com.dbccompany.fipebackend.dto.ManufacturerDto;
import br.com.dbccompany.fipebackend.dto.ModelDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.ui.Model;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("model_cache")
public class ModelCache {
    @Id
    private String id;
    private Integer manufacturerId;
    private Integer vehicleId;
    private LocalDateTime generatedWhen;
    private List<ModelDto> cachedResults;
}
