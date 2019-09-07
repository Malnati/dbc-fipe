package br.com.dbccompany.fipebackend.service;

import br.com.dbccompany.fipebackend.dto.ModelDto;
import br.com.dbccompany.fipebackend.dto.PriceDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface PriceService {

  PriceDto getPriceManufacturerVehicleAndModel(
      @NotNull Integer manufacturerId, @NotNull Integer vehicleId, @NotNull String modelId);
}
