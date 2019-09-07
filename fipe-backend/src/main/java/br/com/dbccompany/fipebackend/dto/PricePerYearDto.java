package br.com.dbccompany.fipebackend.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"year"})
public class PricePerYearDto implements Comparable<PricePerYearDto> {
    private Integer year;
    private BigDecimal value;
    private BigDecimal variation;

    @Override
    public int compareTo(PricePerYearDto o) {
        return year.compareTo(o.getYear());
    }
}
