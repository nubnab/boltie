import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoltieSidenavComponent } from './boltie-sidenav.component';

describe('BoltieSidenavComponent', () => {
  let component: BoltieSidenavComponent;
  let fixture: ComponentFixture<BoltieSidenavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BoltieSidenavComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoltieSidenavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
