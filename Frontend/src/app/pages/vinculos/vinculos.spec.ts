import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Vinculos } from './vinculos';

describe('Vinculos', () => {
  let component: Vinculos;
  let fixture: ComponentFixture<Vinculos>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Vinculos],
    }).compileComponents();

    fixture = TestBed.createComponent(Vinculos);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
