package br.com.dbccompany.fipebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelDto {
  @JsonProperty("fipe_marca")
  private String fipeManufacturer;

  @JsonProperty("fipe_codigo")
  private String fipeCode;

  private String name;

  @JsonProperty("marca")
  private String manufacturer;

  private String key;

  @JsonProperty("veiculo")
  private String vehicle;

  private String id;

  @JsonProperty("id_marca")
  private Integer manufacturerId;

  @JsonProperty("id_veiculo")
  private Integer vehicleId;
}
