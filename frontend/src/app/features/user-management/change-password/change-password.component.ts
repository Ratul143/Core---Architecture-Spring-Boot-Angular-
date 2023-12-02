import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {CreateEditNewUserDialog} from '../user/user.component';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../user/user.service';
import {AuthService} from '../../../core/services/auth.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {CommonService} from 'src/app/core/services/common.service';
import {NotificationService} from 'src/app/core/services/notification.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  changePasswordGroup: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private refDialog: MatDialogRef<CreateEditNewUserDialog>,
    public commonService: CommonService,
    private formBuilder: FormBuilder,
    private notificationService: NotificationService,
    private userService: UserService,
    public auth: AuthService
  ) {}

  submit(): void {
    this.userService
      .changePassword(this.changePasswordGroup.getRawValue())
      .subscribe({
        next: (response: HttpResponse<any>) => {
          this.commonService.AClicked();
          this.resetForm();
          this.notificationService.success('Password changed!');
          this.refDialog.close();
        },
        error: (errorResponse: HttpErrorResponse) => {
          this.notificationService.error(errorResponse.error.message);
        },
      });
  }

  resetForm() {
    this.changePasswordGroup.reset();
  }

  ngOnInit(): void {
    this.changePasswordGroup = this.formBuilder.group(
      {
        currentPassword: ['', Validators.required],
        newPassword: [
          '',
          Validators.compose([Validators.required, Validators.minLength(6)]),
        ],
        confirmNewPassword: ['', Validators.required],
      },
      {
        validator: this.matchingPasswords('newPassword', 'confirmNewPassword'),
      }
    );
  }

  matchingPasswords(passwordKey: string, confirmPasswordKey: string) {
    return (group: FormGroup) => {
      const password = group.controls[passwordKey];
      const confirmPassword = group.controls[confirmPasswordKey];
      if (password.value !== confirmPassword.value) {
        return confirmPassword.setErrors({ mismatchedPasswords: true });
      }
    };
  }
}
