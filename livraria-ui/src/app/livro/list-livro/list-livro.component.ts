import { Component, OnInit , Inject} from '@angular/core';
import {Router} from '@angular/router';
import {Livro} from '../livro';
import {LivroService} from '../livro.service';
import { Observable } from 'rxjs';




@Component({
  selector: 'app-list-livro',
  templateUrl: './list-livro.component.html',
  styleUrls: ['./list-livro.component.css']
})
export class ListLivroComponent implements OnInit {

  livros: Observable<Livro[]>;

  constructor(private livroService: LivroService,
    private router: Router) {
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    }

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    this.livros = this.livroService.listar();
    console.log(this.livros);
  }

  deleteLivro(id: number) {
    this.livroService.remover(id)
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
    this.router.navigate(['add-livro']);
  }
}
