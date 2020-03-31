import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AddLivroComponent} from './livro/add-livro/add-livro.component';
import {ListLivroComponent} from './livro/list-livro/list-livro.component';
import {EditLivroComponent} from './livro/edit-livro/edit-livro.component';
import {UploadFileComponent} from './components/upload-file/upload-file/upload-file.component';
import {DatePickerComponent} from './components/date-picker/date-picker/date-picker.component';
import {UsuarioComponent} from './usuario/usuario.component';
import {ListUsuarioComponent} from './usuario/list-usuario/list-usuario.component';


const routes: Routes = [
  { path: '', redirectTo: 'list-livro', pathMatch: 'full' },
  { path: 'add-livro', component: AddLivroComponent },
  { path: 'list-livro', component: ListLivroComponent },
  { path: 'edit-livro', component: EditLivroComponent },
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
