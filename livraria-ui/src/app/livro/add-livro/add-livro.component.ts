import {Livro} from '../livro';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {LivroService} from '../livro.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DATE_FORMATS } from '@angular/material/core';

export const MY_DATE_FORMATS = {
  parse: {
    dateInput: 'DD/MM/YYYY',
  },
  display: {
    dateInput: 'DD/MM/YYYY',
    monthYearLabel: 'MMMM YYYY',
    dateA11yLabel: 'LL',
    monthYearA11yLabel: 'MMMM YYYY'
  },
};

@Component({
  selector: 'app-add-livro',
  templateUrl: './add-livro.component.html',
  styleUrls: ['./add-livro.component.css'],
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS }
  ]
})
export class AddLivroComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,private livroService: LivroService,
    private router: Router) { }

  form: FormGroup;
  livro: Livro = new Livro();
  submitted = false;
  isSalvarOuEditar: any;
  isDetalhe: any;

  ngOnInit() {

     this.isSalvarOuEditar =  this.livroService.getSalvarOuEditar();
     this.isDetalhe = this.livroService.getDetalhe();
     this.form = this.formBuilder.group({
        id: [],
        isbn: [],
        titulo: ['', Validators.required],
        autor: ['', Validators.required],
        preco: ['', Validators.required],
        dataPublicacao: ['', Validators.required],
        imagemCapa: ['', Validators.required]
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
      this.livroService.salvar(formulario.value)
      .subscribe(data => console.log(data), error => console.log(error));
      this.livro = new Livro();
      this.gotoList();
      } else {
        this.livroService.atualizar(formulario.value)
        .subscribe(data => console.log(data), error => console.log(error));
        this.livro = new Livro();
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
    this.router.navigate(['list-livro']);
  }

}
