import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Usuario} from './usuario';
import {Observable} from 'rxjs/index';
import {ApiResponse} from '../model/api.response';
import {environment} from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) { }
  baseUrl: string = environment.BASE_URL + '/usuarios/';

  listar(): Observable<any> {
    return this.http.get(`${this.baseUrl + 'listar'}`);
  }

  getLivroById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + id);
  }

  salvar(usuario: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl + 'salvar'}`, usuario);
  }

  atualizar(usuario: Usuario): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + usuario.id, usuario);
  }

  remover(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + id);
  }
}
