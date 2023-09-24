import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CyclesComponent } from './cycles/cycles.component';
import { BorrowedCyclesComponent } from './borrowed-cycles/borrowed-cycles.component';
import { AddCyclesComponent } from './add-cycles/add-cycles.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { CartComponent } from './cart/cart.component';
import { RentalRecordComponent } from './rental-record/rental-record.component'; // Import FormsModule


@NgModule({
  declarations: [
    AppComponent,
    CyclesComponent,
    BorrowedCyclesComponent,
    AddCyclesComponent,
    LoginFormComponent,
    CartComponent,
    RentalRecordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
