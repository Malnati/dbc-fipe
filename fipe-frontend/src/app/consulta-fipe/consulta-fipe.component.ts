import { Component, OnInit } from '@angular/core';
import { Marca } from '../entities/Marca';
import { MarcaService} from '../services/marca.service';

@Component({
  selector: 'app-consulta-fipe',
  templateUrl: './consulta-fipe.component.html',
  styleUrls: ['./consulta-fipe.component.css']
})
export class ConsultaFipeComponent implements OnInit {
  marcas: Marca[];

  constructor(private marcaService : MarcaService) { }

  ngOnInit() {
    this.retrieveMarcas();
  }

  retrieveMarcas() : void {
    this.marcaService.getMarcas()
      .subscribe(marcas => this.marcas = marcas);
  }

}
