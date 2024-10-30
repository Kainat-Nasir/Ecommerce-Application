import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-add-product-dialog',
  templateUrl: './add-product-dialog.component.html',
  styleUrls: ['./add-product-dialog.component.css']
})
export class AddProductDialogComponent {
  productForm: FormGroup;
  selectedFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private dialogRef: MatDialogRef<AddProductDialogComponent>
  ) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      productAvailable: [true, Validators.required],
      stockQuantity: ['', [Validators.required, Validators.min(1)]],
    });
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  onSubmit(): void {
    if (this.productForm.valid && this.selectedFile) {
      const formData = new FormData();
      formData.append('name', this.productForm.get('name')?.value);
      formData.append('description', this.productForm.get('description')?.value);
      formData.append('price', this.productForm.get('price')?.value);
      formData.append('productAvailable', this.productForm.get('productAvailable')?.value);
      formData.append('stockQuantity', this.productForm.get('stockQuantity')?.value);
      formData.append('image', this.selectedFile);

      this.productService.addProduct(formData).subscribe(
        () => {
          this.dialogRef.close(true); // Close dialog and refresh product list
        },
        (error) => {
          console.error('Error adding product:', error);
        }
      );
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}