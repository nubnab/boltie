import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StreamKeyWarningComponent } from './stream-key-warning.component';

describe('StreamKeyWarningComponent', () => {
  let component: StreamKeyWarningComponent;
  let fixture: ComponentFixture<StreamKeyWarningComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StreamKeyWarningComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StreamKeyWarningComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
