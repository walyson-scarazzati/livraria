import { Component, OnInit , Inject, Input, Output, ViewChild, ElementRef } from '@angular/core';
import {Router} from '@angular/router';
import {Livro} from '../livro';
import {LivroService} from '../livro.service';
import {
  debounceTime,
  map,
  distinctUntilChanged,
  filter
} from "rxjs/operators";
import { Observable, Subscription, of, fromEvent} from 'rxjs';

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
  @ViewChild('tituloSearchInput', { static: true }) tituloSearchInput: ElementRef;
  @ViewChild('autorSearchInput', { static: true }) autorSearchInput: ElementRef;
  isSearching: boolean;
  apiResponse: any;

  constructor(private livroService: LivroService,
    private router: Router) {
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    }

  ngOnInit() {
    this.reloadData();

    this.searchEventAutor();

    this.searchEventTitulo(); 

  }

  searchEventAutor(){
    fromEvent(this.autorSearchInput.nativeElement, 'keyup').pipe(

      // get value
      map((event: any) => {
        if (event.keyCode === 17 || event.keyCode === 91 || event.keyCode === 37 || event.keyCode === 38 || event.keyCode === 39 || event.keyCode === 40 || event.keyCode === 13 || event.keyCode === 27) {
          return;
        }
        return event.target.value;
      })
      // if character is empty
      , filter((data: any) => {
        if (!data || data == '') {
          this.reloadData();
          return false;
        }
        return true;
      })

      // Time in milliseconds between key events
      , debounceTime(1000)

      // If previous query is diffent from current   
      , distinctUntilChanged()

      // subscription for response
    ).subscribe((text: string) => {

      this.isSearching = true;

      this.searchAutor(text).subscribe( data => {
        this.livroList = data.content;
        this.count = data.totalElements;
        this.isSearching = false;
      
      }, (err) => {
        this.isSearching = false;
        console.log('error', err);
      });

    });
  }

  searchEventTitulo(){
    fromEvent(this.tituloSearchInput.nativeElement, 'keyup').pipe(

      // get value
      map((event: any) => {
        if (event.keyCode === 17 || event.keyCode === 91 || event.keyCode === 37 || event.keyCode === 38 || event.keyCode === 39 || event.keyCode === 40 || event.keyCode === 13 || event.keyCode === 27) {
          return;
        }
        return event.target.value;
      })
      // if character is empty
      , filter((data: any) => {
        if (!data || data == '') {
          this.reloadData();
          return false;
        }
        return true;
      })

      // Time in milliseconds between key events
      , debounceTime(1000)

      // If previous query is diffent from current   
      , distinctUntilChanged()

      // subscription for response
    ).subscribe((text: string) => {

      this.isSearching = true;

      this.searchTitulo(text).subscribe( data => {
        this.livroList = data.content;
        this.count = data.totalElements;
        this.isSearching = false;
      
      }, (err) => {
        this.isSearching = false;
        console.log('error', err);
      });

    });
  }

  searchAutor(autor: string) {
    if (autor === '') {
      return of([]);
    }
    return this.livroService.findByAutor(autor);
  }

  searchTitulo(titulo: string) {
    if (titulo === '') {
      return of([]);
    }
    return this.livroService.findByTitulo(titulo);
  }

  reloadData() {
    const params = this.getRequestParams(this.page, this.size);
   this.livroService.listar(params)
    .subscribe(
       data => {
      this.livroList = data.content;
      this.count = data.totalElements;
    }
    );
  }

  deleteLivro(id: number) {
    this.livroService.remover(id)
      .subscribe(
        data => {
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
