import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Role } from './role';
import { Observable, BehaviorSubject } from 'rxjs/index';
import { tap } from 'rxjs/operators';
import { ApiResponse } from '../model/api.response';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) { }
  baseUrl: string = environment.BASE_URL + '/roles/';

  salvarOuEditar;
  detalhe;

  private rolesSubject = new BehaviorSubject<Role[]>([]);
  roles$: Observable<Role[]> = this.rolesSubject.asObservable();

  carregarRoles(): void {
    this.http.get<Role[]>(environment.BASE_URL + '/usuarios/roles')
      .subscribe(roles => this.rolesSubject.next(roles));
  }

  listar(params): Observable<any> {
    return this.http.get(this.baseUrl + 'listar', { params });
  }

  getRoleById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(`${this.baseUrl}${id}`);
  }

  salvar(role: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl + 'salvar'}`, role)
      .pipe(tap(() => this.carregarRoles()));
  }

  atualizar(role: Role): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.baseUrl + role.id, role)
      .pipe(tap(() => this.carregarRoles()));
  }

  remover(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.baseUrl + id)
      .pipe(tap(() => this.carregarRoles()));
  }

  setSalvarOuEditar(salvarOuEditar) {
    this.salvarOuEditar = salvarOuEditar;
  }

  getSalvarOuEditar() {
    const temp = this.salvarOuEditar;
    this.clearData();
    return temp;
  }

  setDetalhe(detalhe) {
    this.detalhe = detalhe;
  }

  getDetalhe() {
    const temp = this.detalhe;
    this.clearData();
    return temp;
  }

  clearData() {
    this.salvarOuEditar = undefined;
  }

}
