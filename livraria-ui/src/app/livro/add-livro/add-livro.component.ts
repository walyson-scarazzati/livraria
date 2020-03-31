import {Livro} from '../livro';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {LivroService} from '../livro.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-livro',
  templateUrl: './add-livro.component.html',
  styleUrls: ['./add-livro.component.css']
})
export class AddLivroComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,private livroService: LivroService,
    private router: Router) { }


 // addForm: FormGroup;

  livro: Livro = new Livro();
  submitted = false;

  ngOnInit() {
  /*   this.addForm = this.formBuilder.group({
      isbn: [],
      titulo: ['', Validators.required],
      autor: ['', Validators.required],
      preco: ['', Validators.required],
      dataPublicacao: ['', Validators.required],
      imagemCapa: ['', Validators.required]
    }); */

  }

  newLivro(): void {
    this.submitted = false;
    this.livro = new Livro();
  }

      //this.livrariaService.salvar(this.addForm.value)

    save() {

   this.livroService.salvar(this.livro)
     .subscribe(data => console.log(data), error => console.log(error));
 this.livro = new Livro();
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
    this.router.navigate(['list-livro']);
  }

}
