import { Component, OnInit } from '@angular/core';
import { CycleService } from '../app.service';

@Component({
  selector: 'app-rental-record',
  templateUrl: './rental-record.component.html',
  styleUrls: ['./rental-record.component.scss']
})
export class RentalRecordComponent implements OnInit {
  rentalData: any[] = [];
  userData: any[] = [];

  constructor(private cycleService :CycleService) { }

  ngOnInit() {
    this.cycleService.getRentalRecord().subscribe((res: any) => {
      this.rentalData = res;
      this.userData = this.processUserData(this.rentalData);
    });
  }

  processUserData(data: any[]): any[] {
    const result: any[] = [];
    data.forEach((entry) => {
      const existingUser = result.find((user) => user.user.id === entry.user.id);
      if (existingUser) {
        existingUser.totalAmount += entry.totalAmount;
      } else {
        const newUserEntry = { ...entry };
        newUserEntry.rowspan = entry.items.length;
        result.push(newUserEntry);
      }
    });
    return result;
  }
}
