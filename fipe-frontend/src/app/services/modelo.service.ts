import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Modelo } from '../entities/Modelo';

@Injectable({
  providedIn: 'root'
})
export class ModeloService {

  private modelosUrl = 'api/veiculos';  // URL to web api

  constructor(private http: HttpClient) { }

  getModelos(idMarca : number): Observable<Modelo[]> {
    return this.http.get<Modelo[]>(this.modelosUrl+"/"+idMarca);
  }
}
