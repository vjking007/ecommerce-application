import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../../core/api.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private api: ApiService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.pattern(/^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/)
      ]
      ]
    });
  }

   ngOnInit(): void {}

   onSubmit() {
    if (this.registerForm.invalid) {
    this.registerForm.markAllAsTouched();
    return;
    }
    this.loading = true;

    this.api.post('auth/register', this.registerForm.value).subscribe({
      next: (res) => {
        //this.toastr.success('Registration successful');
        this.router.navigate(['/auth/login']);
      },
      error: () => {
        this.toastr.error('Registration failed');
        this.loading = false;
      }
    });
  }
}
