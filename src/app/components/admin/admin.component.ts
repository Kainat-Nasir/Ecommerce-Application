import { Component } from '@angular/core';
import { Product, ProductService } from 'src/app/services/product.service';
import { AddProductDialogComponent } from '../add-product-dialog/add-product-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { UpdateDialogComponent } from '../update-dialog/update-dialog.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  products: Product[] = [];

  constructor(private productService: ProductService, private dialog: MatDialog, private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe(
      (data: Product[]) => {
        this.products = data;
      },
      (error: any) => {
        console.error('Error fetching products:', error);
      }
    );
  }


  removeProduct(productId: number): void {
    this.productService.deleteProduct(productId).subscribe(
        () => {
          // alert("are you sure you want to delete this product");
            this.loadProducts(); 
        },
        error => {
            console.error('Error deleting product:', error);
            
        }
    );
}
UpdateDialog(product: Product): void {
  const dialogRef = this.dialog.open(UpdateDialogComponent, {
    width: '600px',
    data: product // Pass the product data to the dialog
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      // Handle the result after updating the product
      this.loadProducts(); 
    }
  });
}
AddDialog(): void {
  const dialogRef = this.dialog.open(AddProductDialogComponent, {
    width: '400px'
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      this.loadProducts(); // Refresh product list after adding
    }
  });
}
openConfirmDialog(productId: number): void {
  const dialogRef = this.dialog.open(ConfirmDialogComponent, {
    width: '400px',
    data: { message: 'Are you sure you want to delete this product?' }
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
      this.removeProduct(productId);
    }
  });
}

  getImage(url: any){
    if(url){
      return url.replace(/^.*[\\\/]src[\\\/]assets[\\\/]/, 'assets/');
    }
    return "";
  }
  logout(): void {
    this.userService.logout(); // Implement logout functionality in UserService
    this.router.navigate(['/login']);
  }
}

