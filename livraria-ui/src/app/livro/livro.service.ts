import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Livro} from './livro';
import {Observable} from 'rxjs/index';
import {ApiResponse} from '../model/api.response';
import {environment} from '../../environments/environment';



@Injectable({
  providedIn: 'root'
})
export class LivroService {

  constructor(private http: HttpClient) { }
  baseUrl: string = environment.BASE_URL + '/livros/';

  salvarOuEditar;
  detalhe;

  listar(params): Observable<any> {
    return this.http.get(`${this.baseUrl + 'listar'}`, { params });
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

  findByTitulo(titulo: string): Observable<any> {
    return this.http.get(this.baseUrl + '/titulo/' + titulo);
  }

  findByAutor(autor: string): Observable<any> {
    return this.http.get(this.baseUrl + '/autor/' + autor);
  }

  setSalvarOuEditar(salvarOuEditar) {
    this.salvarOuEditar = salvarOuEditar;
  }

  getSalvarOuEditar() {
    let temp = this.salvarOuEditar;
    this.clearData();
    return temp;
  }

  setDetalhe(detalhe) {
    this.detalhe = detalhe;
  }

  getDetalhe() {
    let temp = this.detalhe;
    this.clearData();
    return temp;
  }

  clearData() {
    this.salvarOuEditar = undefined;
  }

}
