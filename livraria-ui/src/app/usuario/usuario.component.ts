import {Usuario} from './usuario';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {UsuarioService} from './usuario.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,private usuarioService: UsuarioService,
    private router: Router) { }


  addForm: FormGroup;

  usuario: Usuario = new Usuario();
  submitted = false;

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      id: [],
      nome: ['', Validators.required],
      email: ['', Validators.required],
      senha: ['', Validators.required]
    });

  }

  newLivraria(): void {
    this.submitted = false;
    this.usuario = new Usuario();
  }

    save() {
   this.usuarioService.salvar(this.addForm.value)
     .subscribe(data => console.log(data), error => console.log(error));
 this.usuario = new Usuario();
  this.gotoList();
 }



 // save() {
 //   this.livrariaService.salvar(this.addForm.value)
 //     .subscribe( data => {
 //       this.router.navigate(['list-livraria']);
 //     });
 // }

 // save() {
 //   this.livrariaService.salvar(this.livraria)
 //     .subscribe(data => console.log(data), error => console.log(error));
  //  this.livraria = new Livraria();
 //   this.gotoList();
 // }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  gotoList() {
    this.router.navigate(['list-usuario']);
  }

}
