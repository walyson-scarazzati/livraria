import { Component, OnInit , Inject} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';
import {Livraria } from '../livraria';
import {LivroService} from '../livro.service';

@Component({
  selector: 'app-edit-livraria',
  templateUrl: './edit-livraria.component.html',
  styleUrls: ['./edit-livraria.component.css']
})
export class EditLivrariaComponent implements OnInit {

  livraria: Livraria;
  editForm: FormGroup;
  submitted = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private apiService: LivroService) { }

  ngOnInit() {
    let livrariaId = window.localStorage.getItem('editLivrariaId');
    if (!livrariaId) {
      alert('Invalid action.');
      this.router.navigate(['list-livraria']);
      return;
    }
    this.editForm = this.formBuilder.group({
      isbn: [''],
      titulo: ['', Validators.required],
      autor: ['', Validators.required],
      preco: ['', Validators.required],
      dataPublicacao: ['', Validators.required],
      imagemCapa: ['', Validators.required]
    });
    this.apiService.getLivroById(+livrariaId)
      .subscribe( data => {
        this.editForm.setValue(data.result);
      });
  }

  onSubmit() {
    this.submitted = true;
    this.apiService.atualizar(this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            alert('User updated successfully.');
            this.router.navigate(['list-livraria']);
          } else {
            alert(data.message);
          }
        },
        error => {
          alert(error);
        });
  }

}
