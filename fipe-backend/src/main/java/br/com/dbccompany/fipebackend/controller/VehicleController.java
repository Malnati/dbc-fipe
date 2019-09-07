package br.com.dbccompany.fipebackend.controller;

import br.com.dbccompany.fipebackend.dto.PriceVariationDto;
import br.com.dbccompany.fipebackend.dto.VehicleDto;
import br.com.dbccompany.fipebackend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/veiculos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleController {

  private final VehicleService service;

  @GetMapping("/{idManufacturer}")
  public Collection<VehicleDto> list(@PathVariable(name = "idManufacturer") int idManufacturer) {
    return service.listVehiclesByManufacturer(idManufacturer);
  }

  @GetMapping("/{idManufacturer}/{idVehicle}")
  public Collection<PriceVariationDto> getPriceVariation(
      @PathVariable(name = "idManufacturer") int idManufacturer,
      @PathVariable(name = "idVehicle") int idVehicle) {
    return service.findPriceVariationByManufacturerAndVehicle(idManufacturer, idVehicle);
  }
}
