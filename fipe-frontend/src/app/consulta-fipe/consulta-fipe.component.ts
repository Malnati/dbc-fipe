import { Component, OnInit } from '@angular/core';
import { Marca } from '../entities/Marca';
import { Modelo } from '../entities/Modelo';
import { PriceVariation } from '../entities/PriceVariation';
import { MarcaService} from '../services/marca.service';
import { ModeloService} from '../services/modelo.service';
import { PriceVariationService} from '../services/price-variation.service';

@Component({
  selector: 'app-consulta-fipe',
  templateUrl: './consulta-fipe.component.html',
  styleUrls: ['./consulta-fipe.component.css']
})
export class ConsultaFipeComponent implements OnInit {
  marcas: Marca[];
  modelos: Modelo[];
  priceVariations: PriceVariation[];
  selectedMarca : number;
  selectedModelo : number;

  constructor(private marcaService : MarcaService,
    private modeloService : ModeloService,
    private priceVariationService : PriceVariationService) { }

  ngOnInit() {
    this.retrieveMarcas();
  }

  retrieveMarcas() : void {
    this.marcaService.getMarcas()
      .subscribe(marcas => this.marcas = marcas);
  }

  selectMarca(idMarca : number) : void {
    console.log(idMarca);
    this.selectedMarca = idMarca;
    if(idMarca) {
      this.modeloService.getModelos(idMarca)
        .subscribe(modelos => this.modelos = modelos);
    }
  }

  selectModelo(idModelo : number) : void {
    this.selectedModelo = idModelo;
  }

  search() {
    console.log("selectedMarca: |"+this.selectedMarca+"|");
    console.log("selectedModelo: |"+this.selectedModelo+"|");
    if(this.selectedMarca && this.selectedModelo) {
      this.priceVariationService.getPriceVariation(this.selectedMarca, this.selectedModelo)
        .subscribe(priceVariations => this.priceVariations = priceVariations);
    }
    else {
      alert('Você deve selecionar marca e modelo para pesquisar')
    }
  }

}
