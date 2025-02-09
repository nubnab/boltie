import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WatchRecordingComponent } from './watch-recording.component';

describe('WatchRecordingComponent', () => {
  let component: WatchRecordingComponent;
  let fixture: ComponentFixture<WatchRecordingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WatchRecordingComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WatchRecordingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
