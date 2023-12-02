import { Injectable, NgZone } from '@angular/core';
import {
  MatSnackBar,
  MatSnackBarConfig,
  MatSnackBarHorizontalPosition,
  MatSnackBarVerticalPosition,
} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  horizontalPosition: MatSnackBarHorizontalPosition = 'end';
  verticalPosition: MatSnackBarVerticalPosition = 'top';
  addExtraClass = false;

  constructor(private snackBar: MatSnackBar, private zone: NgZone) {}

  successSnackBar(message: string, action?: string) {
    this.zone.run(() => {
      const config = new MatSnackBarConfig();
      config.horizontalPosition = this.horizontalPosition;
      config.verticalPosition = this.verticalPosition;
      config.panelClass = ['isa_success'];
      config.duration = 5000;
      this.snackBar.open(message, 'x', config);
    });
  }
  errorSnackBar(message: string, action?: string) {
    this.zone.run(() => {
      const config = new MatSnackBarConfig();
      config.horizontalPosition = this.horizontalPosition;
      config.verticalPosition = this.verticalPosition;
      config.panelClass = ['isa_error'];
      config.duration = 5000;
      this.snackBar.open(message, 'x', config);
    });
  }
  infoSnackBar(message: string, action?: string) {
    this.zone.run(() => {
      const config = new MatSnackBarConfig();
      config.horizontalPosition = this.horizontalPosition;
      config.verticalPosition = this.verticalPosition;
      config.panelClass = ['isa_info'];
      config.duration = 5000;
      this.snackBar.open(message, 'x', config);
    });
  }
  warningSnackBar(message: string, action?: string) {
    this.zone.run(() => {
      const config = new MatSnackBarConfig();
      config.horizontalPosition = this.horizontalPosition;
      config.verticalPosition = this.verticalPosition;
      config.panelClass = ['isa_warning'];
      config.duration = 5000;
      this.snackBar.open(message, 'x', config);
    });
  }
}
