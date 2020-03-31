import { Component, OnInit , Inject} from '@angular/core';
import {Router} from '@angular/router';
import {Usuario} from '../usuario';
import {UsuarioService} from '../usuario.service';
import { Observable } from 'rxjs';




@Component({
  selector: 'app-list-usuario',
  templateUrl: './list-usuario.component.html',
  styleUrls: ['./list-usuario.component.css']
})
export class ListUsuarioComponent implements OnInit {

  usuarios: Observable<Usuario[]>;

  constructor(private usuarioService: UsuarioService,
    private router: Router) {}

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    this.usuarios = this.usuarioService.listar();
    console.log(this.usuarios);
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

  detailsUsuario(id: number){
    this.router.navigate(['usuario', id]);
  }

  editUsuario(id: number){
    this.router.navigate(['usuario', id]);
  }

  addUsuario(): void {
    this.router.navigate(['usuario']);
  }
}
