import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { PriceVariation } from '../entities/PriceVariation';

@Injectable({
  providedIn: 'root'
})
export class PriceVariationService {

  private priceVariationUrl = 'api/veiculos';  // URL to web api

  constructor(private http: HttpClient) { }

  getPriceVariation(idMarca: number, idVeiculo: number): Observable<PriceVariation[]> {
    var url = this.priceVariationUrl+"/"+idMarca+"/"+idVeiculo;
    return this.http.get<PriceVariation[]>(url);
  }
}
