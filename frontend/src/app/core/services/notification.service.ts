import { Injectable } from '@angular/core';
import { NotifierService } from 'angular-notifier';
import { NotificationTypeEnum } from '../classes/notification-type-enum';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  constructor(private notifier: NotifierService) {}

  public default(message: string) {
    if (message) {
      this.notifier.notify(
        NotificationTypeEnum.DEFAULT,
        this.capitalizeFirstLetter(message)
      );
    } else {
      this.notifier.notify(
        NotificationTypeEnum.DEFAULT,
        'Oops! Something went wrong.'
      );
    }
  }

  public info(message: string) {
    if (message) {
      this.notifier.notify(
        NotificationTypeEnum.INFO,
        this.capitalizeFirstLetter(message)
      );
    } else {
      this.notifier.notify(
        NotificationTypeEnum.INFO,
        'Oops! Something went wrong.'
      );
    }
  }

  public success(message: string) {
    if (message) {
      this.notifier.notify(
        NotificationTypeEnum.SUCCESS,
        this.capitalizeFirstLetter(message)
      );
    } else {
      this.notifier.notify(
        NotificationTypeEnum.SUCCESS,
        'Oops! Something went wrong.'
      );
    }
  }

  public warning(message: string) {
    if (message) {
      this.notifier.notify(
        NotificationTypeEnum.WARNING,
        this.capitalizeFirstLetter(message)
      );
    } else {
      this.notifier.notify(
        NotificationTypeEnum.WARNING,
        'Oops! Something went wrong.'
      );
    }
  }

  public error(message: string) {
    if (message) {
      this.notifier.notify(
        NotificationTypeEnum.ERROR,
        this.capitalizeFirstLetter(message)
      );
    } else {
      this.notifier.notify(
        NotificationTypeEnum.ERROR,
        'Oops! Something went wrong.'
      );
    }
  }

  capitalizeFirstLetter(message): string {
    return message
      ? message.charAt(0).toUpperCase() + message.substring(1).toLowerCase()
      : '';
  }

  public warningForRm(message: string) {
    if (message) {
      this.notifier.notify(NotificationTypeEnum.WARNING, message);
    } else {
      this.notifier.notify(
        NotificationTypeEnum.WARNING,
        'Oops! Something went wrong.'
      );
    }
  }
}
