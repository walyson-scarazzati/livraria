import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditLivrariaComponent } from './edit-livraria.component';

describe('EditLivrariaComponent', () => {
  let component: EditLivrariaComponent;
  let fixture: ComponentFixture<EditLivrariaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditLivrariaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditLivrariaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
