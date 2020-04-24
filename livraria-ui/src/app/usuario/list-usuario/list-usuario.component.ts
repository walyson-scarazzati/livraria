import { Component, OnInit , Inject} from '@angular/core';
import {Router} from '@angular/router';
import {Usuario} from '../usuario';
import {UsuarioService} from '../usuario.service';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-list-usuario',
  templateUrl: './list-usuario.component.html',
  styleUrls: ['./list-usuario.component.css']
})
export class ListUsuarioComponent implements OnInit {

  usuarios: Observable<Usuario[]>;
  isSalvarOuEditar = false;
  isDetalhe = false;
  p = 1;
  usuarioList: any[] = [];
  totalRec: number;
  page = 1;

  constructor(private usuarioService: UsuarioService, private router: Router) {
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    }

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
   this.usuarioService.listar()
   .subscribe(
      data => this.usuarioList = data.content,
   );
  }

  deleteUsuario(id: number) {
    this.usuarioService.remover(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  detailsUsuario(id: number) {
    this.usuarioService.setDetalhe(this.isDetalhe);
    this.router.navigate(['usuario']);
  }

  editUsuario(id: number) {
    this.usuarioService.setSalvarOuEditar(this.isSalvarOuEditar);
    this.usuarioService.setDetalhe(!this.isDetalhe);
    this.router.navigate(['usuario']);
  }

  addUsuario(): void {
    this.usuarioService.setSalvarOuEditar(!this.isSalvarOuEditar);
    this.usuarioService.setDetalhe(!this.isDetalhe);
    this.router.navigate(['usuario']);
  }

  procurar(): void {
    this.usuarioService.setSalvarOuEditar(!this.isSalvarOuEditar);
    this.usuarioService.setDetalhe(!this.isDetalhe);
    this.router.navigate(['usuario']);
  }

}
