package br.com.dbccompany.fipebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerDto {

  private String name;

  @JsonProperty("fipe_name")
  private String fipeName;

  private int order;
  private String key;
  private int id;
}
