import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalRecordComponent } from './rental-record.component';

describe('RentalRecordComponent', () => {
  let component: RentalRecordComponent;
  let fixture: ComponentFixture<RentalRecordComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RentalRecordComponent]
    });
    fixture = TestBed.createComponent(RentalRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
