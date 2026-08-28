import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RoleService } from '../role.service';

@Component({
  selector: 'app-list-perfil',
  templateUrl: './list-perfil.component.html',
  styleUrls: ['./list-perfil.component.css']
})
export class ListPerfilComponent implements OnInit {

  isSalvarOuEditar = false;
  isDetalhe = false;
  roleList: any[] = [];
  count = 0;
  page = 1;
  size = 5;

  constructor(private roleService: RoleService, private router: Router) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
  }

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    const params = this.getRequestParams(this.page, this.size);
    this.roleService.listar(params)
      .subscribe(
        data => {
          this.roleList = data.content;
          this.count = data.totalElements;
        }
      );
  }

  handlePageChange(event): void {
    this.page = event;
    this.reloadData();
  }

  getRequestParams(page, size): any {
    const params = {};

    if (page) {
      params[`page`] = page - 1;
    }

    if (size) {
      params[`size`] = size;
    }

    return params;
  }

  deleteRole(id: number) {
    this.roleService.remover(id)
      .subscribe(
        data => {
          this.reloadData();
        },
        error => console.log(error));
  }

  editRole(id: number) {
    this.roleService.setSalvarOuEditar(this.isSalvarOuEditar);
    this.roleService.setDetalhe(!this.isDetalhe);
    this.router.navigate(['perfil'], { queryParams: { id } });
  }

  addRole(): void {
    this.roleService.setSalvarOuEditar(!this.isSalvarOuEditar);
    this.roleService.setDetalhe(!this.isDetalhe);
    this.router.navigate(['perfil']);
  }

}
