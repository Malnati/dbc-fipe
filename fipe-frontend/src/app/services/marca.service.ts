import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Marca } from '../entities/Marca';
import { MARCAS } from '../mocks/mock-marcas';

@Injectable({
  providedIn: 'root'
})
export class MarcaService {

  private marcasUrl = 'api/marcas';  // URL to web api

  constructor(private http: HttpClient) { }

  getMarcas(): Observable<Marca[]> {
    // return of(MARCAS);
    return this.http.get<Marca[]>(this.marcasUrl);
  }
}
