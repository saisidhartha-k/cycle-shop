import { Component, OnInit } from '@angular/core';
import { CycleService } from '../app.service';


@Component({
  selector: 'app-cycles',
  templateUrl: './cycles.component.html',
  styleUrls: ['./cycles.component.scss']
})
export class CyclesComponent implements OnInit{
  // cycleService: any;
  cycles: any;
  showCycles: boolean = true;
  constructor( private cycleService :CycleService){}

  ngOnInit() {
    this.cycleService.getCycles().subscribe((res: any) => {
      this.cycles = res;
    });
  }


  toggleDataVisibility() {
    this.showCycles = !this.showCycles;
  }

  borrowCycle(id: number) {


    this.cycleService.addToCart(id,1).subscribe(

      (response: any) => {

        console.log('PUT request successful:', response);
        // this.cycleService.setData(response);
        this.ngOnInit();
      },

      );
  
  }
}
