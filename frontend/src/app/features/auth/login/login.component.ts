import { Component, OnDestroy, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { ReplaySubject, Subscription, finalize, takeUntil } from 'rxjs';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { NotificationService } from 'src/app/core/services/notification.service';
import { HeaderTypeEnum } from 'src/app/core/classes/header-type-enum';
import { User } from '../../user-management/user/user';
import { UserService } from '../services/user.service';
import { AUTH_STORE_KEY } from 'src/app/core/constants/constants';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit, OnDestroy {
  loginFormGroup: FormGroup;
  subscription: Subscription[] = [];
  loader: boolean = false;
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private auth: AuthService,
    private router: Router,
    private notificationService: NotificationService
  ) {
    this.loginFormGroup = this.formBuilder.group({
      username: [
        '',
        [
          Validators.required,
          Validators.minLength(2),
          Validators.maxLength(15),
        ],
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(4),
          Validators.maxLength(15),
        ],
      ],
    });
  }

  get formControls(): { [key: string]: AbstractControl } {
    return this.loginFormGroup.controls;
  }

  ngOnInit(): void {
    if (this.auth.isUserLoggedIn()) {
      this.router.navigateByUrl('/dashboard');
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  login() {
    this.loader = true;
    this.userService
      .login(this.loginFormGroup.value)
      .pipe(
        takeUntil(this.destroyed$),
        finalize(() => {
          this.loader = false;
        })
      )
      .subscribe({
        next: (res: any) => {
          if (res.status === 200) {
            this.auth.saveTokenToTheLocalStorage(res.body.token);
            this.auth.saveUserToTheLocalStorage(res.body);
            this.router.navigate(['dashboard']);
            this.notificationService.success('Successfully Logged In!');
          } else {
            this.notificationService.info('Unknown error occurred.');
          }
        },
        error: (errorResponse: HttpErrorResponse) => {
          this.notificationService.error(errorResponse.error.message);
          this.loader = false;
        },
      });
  }

  ngOnDestroy(): void {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }
}
