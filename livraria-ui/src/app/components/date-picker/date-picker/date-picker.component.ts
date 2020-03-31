import {Component} from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import {BsDatepickerConfig} from 'ngx-bootstrap/datepicker';

@Component({
  selector: 'app-date-picker',
  templateUrl: './date-picker.component.html'
})
export class DatePickerComponent {
  public ngModel: FormControl = new FormControl(null);
  datePickerConfig: Partial<BsDatepickerConfig>;


constructor(){
  this.datePickerConfig = Object.assign({}, {containerClass: 'theme-dark-blue'});
}

}
