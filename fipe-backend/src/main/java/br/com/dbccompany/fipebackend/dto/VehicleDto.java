package br.com.dbccompany.fipebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDto {

  @JsonProperty("fipe_marca")
  private String fipeManufacturer;

  private String name;

  @JsonProperty("marca")
  private String manufacturer;

  private String key;
  private String id;

  @JsonProperty("fipe_name")
  private String fipeName;

  @JsonProperty("id_marca")
  private Integer manufacturerId;
}
