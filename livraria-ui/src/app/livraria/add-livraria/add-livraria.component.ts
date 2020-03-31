import { LivrariaService } from '../livraria.service';
import {Livraria} from '../livraria';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {LivroService} from '../livro.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-livraria',
  templateUrl: './add-livraria.component.html',
  styleUrls: ['./add-livraria.component.css']
})
export class AddLivrariaComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,private livrariaService: LivroService,
    private router: Router) { }


 // addForm: FormGroup;

  livraria: Livraria = new Livraria();
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

  newLivraria(): void {
    this.submitted = false;
    this.livraria = new Livraria();
  }

      //this.livrariaService.salvar(this.addForm.value)

    save() {

   this.livrariaService.salvar(this.livraria)
     .subscribe(data => console.log(data), error => console.log(error));
 this.livraria = new Livraria();
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
    this.router.navigate(['list-livraria']);
  }

}
