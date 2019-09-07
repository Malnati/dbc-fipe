package br.com.dbccompany.fipebackend.service;

import br.com.dbccompany.fipebackend.dto.ModelDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ModelService {

    List<ModelDto> listModelsByManufacturerAndVehicle(@NotNull Integer manufacturerId, @NotNull Integer vehicleId);
}
