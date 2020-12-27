import { Component, OnInit , Inject, Input, Output, ViewChild, ElementRef } from '@angular/core';
import {Router} from '@angular/router';
import {Usuario} from '../usuario';
import {UsuarioService} from '../usuario.service';
import {
  debounceTime,
  map,
  distinctUntilChanged,
  filter
} from "rxjs/operators";
import { fromEvent } from 'rxjs';
import { Observable, Subscription, of } from 'rxjs';

@Component({
  selector: 'app-list-usuario',
  templateUrl: './list-usuario.component.html',
  styleUrls: ['./list-usuario.component.css']
})
export class ListUsuarioComponent implements OnInit {

  usuarios: Observable<Usuario[]>;
  isSalvarOuEditar = false;
  isDetalhe = false;
  usuarioList: any[] = [];
  roles: [];
  count = 0;
  page = 1;
  size = 2;
  currentIndex = -1;
  @ViewChild('nomeSearchInput', { static: true }) nomeSearchInput: ElementRef;
  isSearching: boolean;
  apiResponse: any;


  constructor(private usuarioService: UsuarioService, private router: Router) {
      this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    }

  ngOnInit() {
    this.usuarioService.listarRoles().subscribe(
      (response) => {
        console.log('response received');
        this.roles = response;
      });

    this.reloadData();

    fromEvent(this.nomeSearchInput.nativeElement, 'keyup').pipe(

      // get value
      map((event: any) => {
        return event.target.value;
      })
      // if character length greater then 2
      , filter(data => data.length > 2)

      // Time in milliseconds between key events
      , debounceTime(1000)

      // If previous query is diffent from current   
      , distinctUntilChanged()

      // subscription for response
    ).subscribe((text: string) => {

      this.isSearching = true;

      this.searchGetCall(text).subscribe( data => {
        this.usuarioList = data.content;
        this.count = data.totalElements;
        console.log(this.count);
        this.isSearching = false;
      
      }, (err) => {
        this.isSearching = false;
        console.log('error', err);
      });

    });

  }

  searchGetCall(nome: string) {
    if (nome === '') {
      return of([]);
    }
    return this.usuarioService.findByNome(nome);
  }

  reloadData() {
   const params = this.getRequestParams(this.page, this.size);
   this.usuarioService.listar(params)
    .subscribe(
       data => {
      this.usuarioList = data.content;
      this.count = data.totalElements;
      console.log(this.count);
    }
    );

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

  deleteUsuario(id: number) {
    this.usuarioService.remover(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  detailsUsuario(id: number) {
    this.usuarioService.setDetalhe(this.isDetalhe);
    this.router.navigate(['usuario']);
  }

  editUsuario(id: number) {
    this.usuarioService.setSalvarOuEditar(this.isSalvarOuEditar);
    this.usuarioService.setDetalhe(!this.isDetalhe);
    this.router.navigate(['usuario']);
  }

  addUsuario(): void {
    this.usuarioService.setSalvarOuEditar(!this.isSalvarOuEditar);
    this.usuarioService.setDetalhe(!this.isDetalhe);
    this.router.navigate(['usuario']);
  }

  procurar(): void {
    this.usuarioService.setSalvarOuEditar(!this.isSalvarOuEditar);
    this.usuarioService.setDetalhe(!this.isDetalhe);
    this.router.navigate(['usuario']);
  }

}
