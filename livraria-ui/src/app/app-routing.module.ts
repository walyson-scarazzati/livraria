import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AddLivrariaComponent} from './livraria/add-livraria/add-livraria.component';
import {ListLivrariaComponent} from './livraria/list-livraria/list-livraria.component';
import {EditLivrariaComponent} from './livraria/edit-livraria/edit-livraria.component';
import {UploadFileComponent} from './components/upload-file/upload-file/upload-file.component';
import {DatePickerComponent} from './components/date-picker/date-picker/date-picker.component';
import {UsuarioComponent} from './usuario/usuario.component';
import {ListUsuarioComponent} from './usuario/list-usuario/list-usuario.component';


const routes: Routes = [
  { path: '', redirectTo: 'list-livraria', pathMatch: 'full' },
  { path: 'add-livraria', component: AddLivrariaComponent },
  { path: 'list-livraria', component: ListLivrariaComponent },
  { path: 'edit-livraria', component: EditLivrariaComponent },
  { path: 'upload-file', component: UploadFileComponent },
  { path: 'date-picker', component: DatePickerComponent },
  { path: 'list-usuario', component: ListUsuarioComponent },
  { path: 'usuario', component: UsuarioComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
