import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Product, ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-update-dialog',
  templateUrl: './update-dialog.component.html',
  styleUrls: ['./update-dialog.component.css']
})
export class UpdateDialogComponent {
  productForm: FormGroup;
  selectedFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<UpdateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, // Data passed from the calling component
    private productService: ProductService
  ) {
    // Initialize the form with the existing product data
    this.productForm = this.fb.group({
      name: [data.name, Validators.required],
      description: [data.description, Validators.required],
      price: [data.price, [Validators.required, Validators.min(0)]],
      productAvailable: [data.productAvailable, Validators.required],
      stockQuantity: [data.stockQuantity, [Validators.required, Validators.min(1)]]
    });
  }

  // Handle file selection
  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  // Cancel and close the dialog
  onCancel(): void {
    this.dialogRef.close();
  }

  // Submit the updated product data
  onSubmit(): void {
    if (this.productForm.valid) {
      const updatedProduct = this.productForm.value;

      const formData = new FormData();
      formData.append('name', updatedProduct.name);
      formData.append('description', updatedProduct.description);
      formData.append('price', updatedProduct.price.toString());
      formData.append('productAvailable', updatedProduct.productAvailable);
      formData.append('stockQuantity', updatedProduct.stockQuantity.toString());

      if (this.selectedFile) {
        formData.append('image', this.selectedFile);
      }

      this.productService.updateProduct(this.data.id, formData).subscribe({
        next: (response) => {
          console.log('Product updated successfully', response);
          this.dialogRef.close(response); // Close the dialog and pass the response back
        },
        error: (error) => {
          console.error('Error updating product:', error);
        }
      });
    }
  }
}