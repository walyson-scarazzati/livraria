import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListLivrariaComponent } from './list-livraria.component';

describe('ListLivrariaComponent', () => {
  let component: ListLivrariaComponent;
  let fixture: ComponentFixture<ListLivrariaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListLivrariaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListLivrariaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
