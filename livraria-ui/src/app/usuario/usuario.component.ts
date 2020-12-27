
import {Usuario} from './usuario';
import { Component, OnInit, Input, HostListener, Output } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {UsuarioService} from './usuario.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private usuarioService: UsuarioService,
              private router: Router, private route: ActivatedRoute) { }


  form: FormGroup;

  usuario: Usuario = new Usuario();
  submitted = false;
  isSalvarOuEditar: any;
  isDetalhe: any;
  roles: [];

  ngOnInit() {
   this.usuarioService.listarRoles().subscribe(
    (response) => {
      console.log('response received');
      this.roles = response;
    });

   this.isSalvarOuEditar =  this.usuarioService.getSalvarOuEditar();
   this.isDetalhe = this.usuarioService.getDetalhe();
   console.log(this.isSalvarOuEditar);
   this.form = this.formBuilder.group({
      id: [],
      nome: ['', Validators.required],
      email: ['', Validators.required],
      senha: ['', Validators.required]
    });

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
    if (formulario.id === undefined) {
      this.usuarioService.salvar(formulario.value)
      .subscribe(data => console.log(data), error => console.log(error));
      this.usuario = new Usuario();
      this.gotoList();
      } else {
        this.usuarioService.atualizar(formulario.value)
        .subscribe(data => console.log(data), error => console.log(error));
        this.usuario = new Usuario();
        this.gotoList();
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
