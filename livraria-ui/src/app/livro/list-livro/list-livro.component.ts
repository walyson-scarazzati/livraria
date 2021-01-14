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
  count = 0;
  page = 1;
  size = 2;
  currentIndex = -1;
  livroList: any[] = [];
  isSalvarOuEditar = false;
  isDetalhe = false;

  constructor(private livroService: LivroService,
    private router: Router) {
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    }

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    
    console.log(this.livros);
    const params = this.getRequestParams(this.page, this.size);
   this.livroService.listar(params)
    .subscribe(
       data => {
      this.livroList = data.content;
      this.count = data.totalElements;
      console.log(this.count);
    }
    );
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
    this.router.navigate(['edit-livro', id]);
  }

  addLivro(): void {
    this.livroService.setSalvarOuEditar(!this.isSalvarOuEditar);
    this.livroService.setDetalhe(!this.isDetalhe);
    this.router.navigate(['add-livro']);
  }

  handlePageChange(event): void {
    this.page = event;
    this.reloadData();
  }

  getRequestParams(page, size): any {
    // tslint:disable-next-line:prefer-const
    let params = {};

    if (page) {
      params[`page`] = page - 1;
    }

    if (size) {
      params[`size`] = size;
    }

    return params;
  }

  procurar(): void {
    this.livroService.setSalvarOuEditar(!this.isSalvarOuEditar);
    this.livroService.setDetalhe(!this.isDetalhe);
    this.router.navigate(['usuario']);
  }

}
