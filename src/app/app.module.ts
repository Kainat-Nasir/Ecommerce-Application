import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { ProductComponent } from './components/product/product.component';
import { CartComponent } from './components/cart/cart.component'; 
import { AdminComponent } from './components/admin/admin.component';
import { AddProductDialogComponent } from './components/add-product-dialog/add-product-dialog.component';
import { ConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';
import { UpdateDialogComponent } from './components/update-dialog/update-dialog.component';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatDialogModule } from '@angular/material/dialog';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenService } from './components/interceptors/token.service';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatSelectModule } from '@angular/material/select';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
@NgModule({
 
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    ProductComponent,
    CartComponent,
    AdminComponent,
    AddProductDialogComponent,
    ConfirmDialogComponent,
    UpdateDialogComponent,
    

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule,
    FormsModule,
    MatCardModule,
    HttpClientModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatIconModule,
    MatInputModule,
    MatButtonModule,
    MatTableModule,
    MatIconModule,
    MatToolbarModule,
    MatDialogModule
  ],

      providers: [
        { provide: HTTP_INTERCEPTORS, useClass: TokenService, multi: true }
      ],
  bootstrap: [AppComponent]
})
export class AppModule { }
