package br.com.dbccompany.fipebackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceDto {
  @JsonProperty("referencia")
  private String reference;

  @JsonProperty("fipe_codigo")
  private String fipeCode;

  @JsonProperty("combustivel")
  private String fuelType;

  @JsonProperty("marca")
  private String manufacturer;

  @JsonProperty("ano_modelo")
  private String yearOfModel;

  @JsonProperty("preco")
  private String price;

  @JsonProperty("preco_sem_formatacao")
  private Double rawPrice;

  private String key;
  private Double time;

  @JsonProperty("veiculo")
  private String vehicle;

  private String id;
}
