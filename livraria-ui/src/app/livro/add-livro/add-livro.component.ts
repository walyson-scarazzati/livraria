import {Livro} from '../livro';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
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
    private router: Router, private route: ActivatedRoute) { }

  form: FormGroup;
  livro: Livro = new Livro();
  submitted = false;
  isSalvarOuEditar: any;
  isDetalhe: any;
  imagemSelecionada: File;
  enviandoImagem = false;

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

     const id = this.route.snapshot.queryParams['id'];
     if (id) {
       this.livroService.getLivroById(+id).subscribe(
         (data: any) => {
           data.dataPublicacao = new Date(data.dataPublicacao);
           this.livro = data;
         },
         error => console.log(error));
     }

  }

  onImagemSelecionada(event) {
    const arquivo: File = event.target.files && event.target.files[0];
    if (!arquivo) {
      return;
    }
    this.imagemSelecionada = arquivo;
    this.enviandoImagem = true;
    this.livroService.uploadImagem(arquivo).subscribe(
      (url: string) => {
        this.livro.imagemCapa = url;
        this.enviandoImagem = false;
      },
      error => {
        console.log(error);
        this.enviandoImagem = false;
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
    if (formulario.value.id === undefined || formulario.value.id === null) {
      this.livroService.salvar(formulario.value)
      .subscribe(data => this.gotoList(), error => console.log(error));
      } else {
        this.livroService.atualizar(formulario.value)
        .subscribe(data => this.gotoList(), error => console.log(error));
    }

   }

  onSubmit(formulario) {
    this.submitted = true;
    if (this.enviandoImagem || formulario.invalid) {
      return;
  } else {
    this.saveOrUpdate(formulario);
  }

  }

  gotoList() {
    this.router.navigate(['list-livro']);
  }

}
