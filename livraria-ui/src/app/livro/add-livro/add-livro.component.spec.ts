import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddLivroComponent } from './add-livro.component';

describe('AddLivroComponent', () => {
  let component: AddLivroComponent;
  let fixture: ComponentFixture<AddLivroComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddLivroComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddLivroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
