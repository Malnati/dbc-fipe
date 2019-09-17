package br.com.dbccompany.fipebackend.controller;

import br.com.dbccompany.fipebackend.dto.PriceVariationDto;
import br.com.dbccompany.fipebackend.service.PriceVariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/preco")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PriceVariationController {

  private final PriceVariationService priceVariationService;

  @GetMapping("/{idManufacturer}/{idVehicle}")
  public Collection<PriceVariationDto> getPriceVariation(
      @PathVariable(name = "idManufacturer") int idManufacturer,
      @PathVariable(name = "idVehicle") int idVehicle) {
    return priceVariationService.findPriceVariationByManufacturerAndVehicle(idManufacturer, idVehicle);
  }
}
