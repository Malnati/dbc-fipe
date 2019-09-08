import { Component, OnInit } from '@angular/core';
import { Marca } from '../entities/Marca';
import { Modelo } from '../entities/Modelo';
import { MarcaService} from '../services/marca.service';
import { ModeloService} from '../services/modelo.service';

@Component({
  selector: 'app-consulta-fipe',
  templateUrl: './consulta-fipe.component.html',
  styleUrls: ['./consulta-fipe.component.css']
})
export class ConsultaFipeComponent implements OnInit {
  marcas: Marca[];
  modelos: Modelo[];
  selectedMarca : number;
  selectedModelo : number;

  constructor(private marcaService : MarcaService,
    private modeloService : ModeloService) { }

  ngOnInit() {
    this.retrieveMarcas();
  }

  retrieveMarcas() : void {
    this.marcaService.getMarcas()
      .subscribe(marcas => this.marcas = marcas);
  }

  selectMarca(idMarca : number) : void {
    this.selectedMarca = idMarca;
    if(idMarca) {
      this.modeloService.getModelos(idMarca)
        .subscribe(modelos => this.modelos = modelos);
    }
  }

  selectModelo(idModelo : number) : void {
    this.selectedModelo = idModelo;
  }

}
