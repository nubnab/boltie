import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserRecordingsComponent } from './user-recordings.component';

describe('RecordingComponent', () => {
  let component: UserRecordingsComponent;
  let fixture: ComponentFixture<UserRecordingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserRecordingsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserRecordingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
