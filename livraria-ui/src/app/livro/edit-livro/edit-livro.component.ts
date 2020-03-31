import { Component, OnInit , Inject} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';
import {Livro } from '../livro';
import {LivroService} from '../livro.service';

@Component({
  selector: 'app-edit-livro',
  templateUrl: './edit-livro.component.html',
  styleUrls: ['./edit-livro.component.css']
})
export class EditLivroComponent implements OnInit {

  livro: Livro;
  editForm: FormGroup;
  submitted = false;
  constructor(private formBuilder: FormBuilder, private router: Router, private livroService: LivroService) { }

  ngOnInit() {
    let livroId = window.localStorage.getItem('editLivroId');
    if (!livroId) {
      alert('Invalid action.');
      this.router.navigate(['list-livro']);
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
    this.livroService.getLivroById(+livroId)
      .subscribe( data => {
        this.editForm.setValue(data.result);
      });
  }

  onSubmit() {
    this.submitted = true;
    this.livroService.atualizar(this.editForm.value)
      .pipe(first())
      .subscribe(
        data => {
          if (data.status === 200) {
            alert('User updated successfully.');
            this.router.navigate(['list-livro']);
          } else {
            alert(data.message);
          }
        },
        error => {
          alert(error);
        });
  }

}
