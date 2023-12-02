import { HttpHeaders } from '@angular/common/http';
import { ChangeDetectorRef, ViewRef, inject } from '@angular/core';
import { Observable, ReplaySubject, UnaryFunction, takeUntil } from 'rxjs';
import { TableColumn } from 'src/app/utility/utils';

export const CONTENT_TYPE = 'application/json';
export const REQUEST_TIMEOUT_IN_SECONDS = '30';
export const nidNoRegEx = '^([0-9]{10}|[0-9]{13}|[0-9]{17})$';
export const passportNoRegEx = '^([0-9]{7})$';
export const birthDrivingNoRegEx = '^([0-9]{8,35})$';
export const mobileNoRegEx = '^(0|[0+]880)1[3-9][0-9]{8}$';
export const AUTH_STORE_KEY = 'accesstoken.cookie.store.key';
export const SIDENAV_ELEMENTS_KEY = 'sidenav.elements.key';
export const REGISTRATION_REF_ID = 'registrationRefId';
export const OTP_EXPIRES_AT = 'otpExpiresAt';
export const USER_INFO = 'user.store.info';
export const PASSWORD = 'password';
export const SAVE_ID = 'saveId';
export const REF_ID = 'refId';
export const OTP_KEY = 'otpKey';
export const REGEX_EMAIL = /[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z0-9.-]{1,}$/;
export const CUSTOMER_ID_REGEX = /[0-9]{8}/;
export const ACCOUNT_NO_REGEX = /^([0-9\-]{14}|[0-9\-]{15})$/;
export const MOBILE_NO_REGEX = /^((01[3-9]{1})[0-9]{8})$/;
export const MOBILE_NO_REGEX_V2 = /^((\+88)|(\+8801[3-9]{1})[0-9]{8})$/;
export const NID_REGEX = /^(\d{10}|\d{13}|\d{17})$/;
export const PASS_REGEX = /^([\w\W]{8})/;
export const EMAIL_REGEX = /[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z0-9.-]{1,}$/;
export const DEFAULT_OFFSET = 1;
export const DEFAULT_LIMIT = 10;
export const CREATE_ACTION = 'create';
export const UPDATE_ACTION = 'update';
export const DELETE_ACTION = 'delete';
export const READ_ACTION = 'read';
export const SEARCH = 'search';
export const SELECT = 'select';
export const FILTER = 'filter';

export function getHttpHeaders(): HttpHeaders {
  return new HttpHeaders()
    .set('content-type', CONTENT_TYPE)
    .set('accept', CONTENT_TYPE);
}

export function getColumns(keys: any): TableColumn[] {
  let columns: TableColumn[] = [];
  keys.forEach((k: any, i: number) => {
    let col = new TableColumn();
    if (k === 'oid') return;
    col.columnDef = k;
    col.header = k;
    columns.push(col);
  });
  return columns;
}

export function takeUntilDestroy$<T>(): UnaryFunction<
  Observable<T>,
  Observable<T>
> {
  const viewRef = inject(ChangeDetectorRef) as ViewRef;
  const destroyer$ = new ReplaySubject<void>(1);

  viewRef.onDestroy(() => destroyer$.next());

  return (observable: Observable<T>) => observable.pipe(takeUntil(destroyer$));
}
