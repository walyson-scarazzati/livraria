import { Component, OnInit , Inject} from '@angular/core';
import {Router} from '@angular/router';
import {Livraria} from '../livraria';
import {LivroService} from '../livro.service';
import { Observable } from 'rxjs';




@Component({
  selector: 'app-list-livraria',
  templateUrl: './list-livraria.component.html',
  styleUrls: ['./list-livraria.component.css']
})
export class ListLivrariaComponent implements OnInit {

  livros: Observable<Livraria[]>;

  constructor(private livrariaService: LivroService,
    private router: Router) {}

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    this.livros = this.livrariaService.listar();
    console.log(this.livros);
  }

  deleteLivro(id: number) {
    this.livrariaService.remover(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  detailsLivro(id: number){
    this.router.navigate(['details', id]);
  }

  editLivro(id: number){
    this.router.navigate(['update', id]);
  }

  addLivro(): void {
    this.router.navigate(['add-livraria']);
  }
}
