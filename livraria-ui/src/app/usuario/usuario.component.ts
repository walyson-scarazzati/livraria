
import {Usuario} from './usuario';
import { Component, OnInit, Input, HostListener, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {UsuarioService} from './usuario.service';
import { RoleService } from '../perfil/role.service';
import { Observable } from 'rxjs/index';
import { Role } from '../perfil/role';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private usuarioService: UsuarioService,
              private roleService: RoleService,
              private router: Router, private route: ActivatedRoute) { }


  form: FormGroup;

  usuario: Usuario = new Usuario();
  submitted = false;
  isSalvarOuEditar: any;
  isDetalhe: any;
  roles$: Observable<Role[]> = this.roleService.roles$;

  ngOnInit() {
   this.isSalvarOuEditar =  this.usuarioService.getSalvarOuEditar();
   this.isDetalhe = this.usuarioService.getDetalhe();
   this.form = this.formBuilder.group({
      id: [],
      nome: ['', Validators.required],
      email: ['', Validators.required],
      senha: ['', Validators.required]
    });

   const id = this.route.snapshot.queryParams['id'];
   if (id) {
     this.usuarioService.getUsuarioById(+id).subscribe(
       (data: any) => this.usuario = data,
       error => console.log(error));
   }

  }

  compareRoles(role1: Role, role2: Role): boolean {
    return role1 && role2 ? role1.id === role2.id : role1 === role2;
  }

  verificaValidTouched(campo) {
    return !campo.valid && this.submitted;
  }

  aplicaCssErro(campo) {
    return {
      'has-error': this.verificaValidTouched(campo),
      'has-feedback': this.verificaValidTouched(campo)
    };
  }

  saveOrUpdate(formulario) {
    if (formulario.value.id === undefined || formulario.value.id === null) {
      this.usuarioService.salvar(formulario.value)
      .subscribe(data => this.gotoList(), error => console.log(error));
      } else {
        this.usuarioService.atualizar(formulario.value)
        .subscribe(data => this.gotoList(), error => console.log(error));
    }

   }

  onSubmit(formulario) {
    this.submitted = true;
    if (formulario.invalid) {
      return;
  } else {
    this.saveOrUpdate(formulario);
  }

  }

  gotoList() {
    this.router.navigate(['list-usuario']);
  }

}
