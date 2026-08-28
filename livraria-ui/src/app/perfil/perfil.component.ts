import { Role } from './role';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { RoleService } from './role.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private roleService: RoleService,
              private router: Router, private route: ActivatedRoute) { }

  form: FormGroup;

  role: Role = new Role();
  submitted = false;
  isSalvarOuEditar: any;
  isDetalhe: any;

  ngOnInit() {
    this.isSalvarOuEditar = this.roleService.getSalvarOuEditar();
    this.isDetalhe = this.roleService.getDetalhe();
    this.form = this.formBuilder.group({
      id: [],
      descricao: ['', Validators.required]
    });

    const id = this.route.snapshot.queryParams['id'];
    if (id) {
      this.roleService.getRoleById(+id).subscribe(
        (data: any) => this.role = data,
        error => console.log(error));
    }
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
      this.roleService.salvar(formulario.value)
        .subscribe(data => this.gotoList(), error => console.log(error));
    } else {
      this.roleService.atualizar(formulario.value)
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
    this.router.navigate(['list-perfil']);
  }

}
