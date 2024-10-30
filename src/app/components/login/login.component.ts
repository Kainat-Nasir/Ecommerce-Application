import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
  
      this.userService.login(username, password).subscribe(
        (user: any) => {
          alert("logged in");
            // localStorage.setItem('token', user.token);
            // localStorage.setItem('userRole', user.roles);
            // localStorage.setItem('userId', user.id);
    
          // Navigate based on the user role
          if (user.roles.includes('Admin')) {
            this.router.navigate(['/Admin']); // Navigate to the admin page
          } else {
            this.router.navigate(['/products']); // Navigate to the products page
          }
        },
        error => {
          // Handle login error
          console.error('Login failed', error);
          this.errorMessage = 'Invalid username or password'; // Display error message
        }
      );
    }
  }
  

  goToRegister() {
    this.router.navigate(['/register']);
  }
}