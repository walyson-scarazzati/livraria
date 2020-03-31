import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DatePickerRoutingModule } from './date-picker-routing.module';
import { DatePickerComponent } from './date-picker/date-picker.component';

@NgModule({
  declarations: [DatePickerComponent],
  exports: [DatePickerComponent],
  imports: [
    CommonModule,
    DatePickerRoutingModule
  ]
})
export class DatePickerModule { }
