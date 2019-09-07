package br.com.dbccompany.fipebackend.controller;

import br.com.dbccompany.fipebackend.dto.ManufacturerDto;
import br.com.dbccompany.fipebackend.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/marcas")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ManufacturerController {

    private final ManufacturerService service;

    @GetMapping
    public Collection<ManufacturerDto> list() {
        return service.listManufacturers();
    }

}
