import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditLivroComponent } from './edit-livro.component';

describe('EditLivroComponent', () => {
  let component: EditLivroComponent;
  let fixture: ComponentFixture<EditLivroComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditLivroComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditLivroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
