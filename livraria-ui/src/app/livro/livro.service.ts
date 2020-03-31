import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Livro} from './livro';
import {Observable} from 'rxjs/index';
import {ApiResponse} from '../model/api.response';


@Injectable({
  providedIn: 'root'
})
export class LivroService {

  constructor(private http: HttpClient) { }
  baseUrl: string = 'http://localhost:9191/springboot-crud-rest/livros/';

  listar(): Observable<any> {
    return this.http.get(`${this.baseUrl + 'listar'}`);
  }

  getLivroById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + id);
  }

  salvar(livro: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl + 'salvar'}`, livro);
  }



  atualizar(livro: Livro): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + livro.isbn, livro);
  }

  remover(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + id);
  }
}
