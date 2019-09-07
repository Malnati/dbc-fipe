package br.com.dbccompany.fipebackend.service;

import br.com.dbccompany.fipebackend.dto.PriceVariationDto;
import br.com.dbccompany.fipebackend.dto.VehicleDto;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

public interface VehicleService {

  List<VehicleDto> listVehiclesByManufacturer(@NotNull Integer manufacturerId);

  Collection<PriceVariationDto> findPriceVariationByManufacturerAndVehicle(
      @NotNull Integer manufacturerId, @NotNull Integer vehicleId);
}
