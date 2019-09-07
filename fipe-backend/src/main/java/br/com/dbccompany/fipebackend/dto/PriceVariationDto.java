package br.com.dbccompany.fipebackend.dto;

import lombok.*;

import java.util.Set;
import java.util.TreeSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"vechicle"})
public class PriceVariationDto {

  private String vehicle;
  private Set<PricePerYearDto> prices;

  public PriceVariationDto(String vehicle) {
    this.vehicle = vehicle;
    this.prices = new TreeSet<>();
  }
}
