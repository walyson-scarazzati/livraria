import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ListLivroComponent } from './livro/list-livro/list-livro.component';
import { AddLivroComponent } from './livro/add-livro/add-livro.component';
import { EditLivroComponent } from './livro/edit-livro/edit-livro.component';
import {UploadFileComponent} from './components/upload-file/upload-file/upload-file.component';
import {DatePickerComponent} from './components/date-picker/date-picker/date-picker.component';
import {BsDatepickerModule} from 'ngx-bootstrap/datepicker';
import {BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import {UsuarioComponent} from './usuario/usuario.component';
import {ListUsuarioComponent} from './usuario/list-usuario/list-usuario.component';
import {SharedModule} from './components/shared.module';
import { NgxPaginationModule } from 'ngx-pagination';
import {NavBarComponent} from './nav-bar/nav-bar.component';
import { CurrencyMaskModule } from 'ng2-currency-mask';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    ListLivroComponent,
    AddLivroComponent,
    EditLivroComponent,
    UploadFileComponent,
    DatePickerComponent,
    UsuarioComponent,
    ListUsuarioComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BsDatepickerModule.forRoot(),
    HttpClientModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    ReactiveFormsModule,
    MaterialModule,
    SharedModule,
    NgxPaginationModule,
    CurrencyMaskModule
  ],
  exports: [
    MaterialModule,
    NgxPaginationModule,
    CurrencyMaskModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
