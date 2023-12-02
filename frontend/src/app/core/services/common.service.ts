import { EventEmitter, Injectable } from '@angular/core';
import { _ComponentService } from 'src/app/features/module-management/component/component.service';
import { ApprovalFlowStatusEnum } from 'src/app/features/settings/approval-flow/enum/approval-flow-status-enum';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CommonService {
  host: string;
  aClickedEvent: EventEmitter<any> = new EventEmitter();
  refreshAside: EventEmitter<any> = new EventEmitter();
  modelCloseEvent: EventEmitter<any> = new EventEmitter();
  baseUrl = environment.BASE_URL;
  weeks = [
    { id: 1, week: 'WEEK-1' },
    { id: 2, week: 'WEEK-2' },
    { id: 3, week: 'WEEK-3' },
    { id: 4, week: 'WEEK-4' },
    { id: 5, week: 'WEEK-5' },
    { id: 6, week: 'WEEK-6' },
    { id: 7, week: 'WEEK-7' },
    { id: 8, week: 'WEEK-8' },
    { id: 9, week: 'WEEK-9' },
    { id: 10, week: 'WEEK-10' },
    { id: 11, week: 'WEEK-11' },
    { id: 12, week: 'WEEK-12' },
    { id: 13, week: 'WEEK-13' },
    { id: 14, week: 'WEEK-14' },
    { id: 15, week: 'WEEK-15' },
    { id: 16, week: 'WEEK-16' },
    { id: 17, week: 'WEEK-17' },
    { id: 18, week: 'WEEK-18' },
    { id: 19, week: 'WEEK-19' },
    { id: 20, week: 'WEEK-20' },
    { id: 21, week: 'WEEK-21' },
    { id: 22, week: 'WEEK-22' },
    { id: 23, week: 'WEEK-23' },
    { id: 24, week: 'WEEK-24' },
    { id: 25, week: 'WEEK-25' },
    { id: 26, week: 'WEEK-26' },
    { id: 27, week: 'WEEK-27' },
    { id: 28, week: 'WEEK-28' },
    { id: 29, week: 'WEEK-29' },
    { id: 30, week: 'WEEK-30' },
    { id: 31, week: 'WEEK-31' },
    { id: 32, week: 'WEEK-32' },
    { id: 33, week: 'WEEK-33' },
    { id: 34, week: 'WEEK-34' },
    { id: 35, week: 'WEEK-35' },
    { id: 36, week: 'WEEK-36' },
    { id: 37, week: 'WEEK-37' },
    { id: 38, week: 'WEEK-38' },
    { id: 39, week: 'WEEK-39' },
    { id: 40, week: 'WEEK-40' },
    { id: 41, week: 'WEEK-41' },
    { id: 42, week: 'WEEK-42' },
    { id: 43, week: 'WEEK-43' },
    { id: 44, week: 'WEEK-44' },
    { id: 45, week: 'WEEK-45' },
    { id: 46, week: 'WEEK-46' },
    { id: 47, week: 'WEEK-47' },
    { id: 48, week: 'WEEK-48' },
    { id: 49, week: 'WEEK-49' },
    { id: 50, week: 'WEEK-50' },
    { id: 51, week: 'WEEK-51' },
    { id: 52, week: 'WEEK-52' },
  ];
  months = [
    { id: 1, month: 'JANUARY' },
    { id: 2, month: 'FEBRUARY' },
    { id: 3, month: 'MARCH' },
    { id: 4, month: 'APRIL' },
    { id: 5, month: 'MAY' },
    { id: 6, month: 'JUNE' },
    { id: 7, month: 'JULY' },
    { id: 8, month: 'AUGUST' },
    { id: 9, month: 'SEPTEMBER' },
    { id: 10, month: 'OCTOBER' },
    { id: 11, month: 'NOVEMBER' },
    { id: 12, month: 'DECEMBER' },
  ];
  attributes = [
    { id: 1, name: 'Order No' },
    { id: 2, name: 'Sample No' },
    { id: 3, name: 'Factory Id' },
    { id: 4, name: 'Unit Name' },
    { id: 5, name: 'Customer Name' },
    { id: 6, name: 'Mkt. Employee Name' },
    { id: 7, name: 'Buyer Name' },
    { id: 8, name: 'Third Party Test Req.' },
    { id: 9, name: 'Certification Req.' },
    { id: 10, name: 'Type of Button' },
    { id: 11, name: 'Leign' },
    { id: 12, name: 'Finished Thickness' },
    { id: 13, name: 'Blank Thickness' },
    { id: 14, name: 'Logo Type' },
    { id: 15, name: 'Color Name' },
    { id: 16, name: 'Order Qty.' },
    { id: 17, name: 'Process of Casting' },
    { id: 18, name: 'Required No. of Sheet' },
    { id: 19, name: 'Req. total RM Quantity' },
    { id: 20, name: 'Per Sheet Weight' },
    { id: 21, name: 'Status' },
  ];
  units = [
    { id: 1, unit: 'OVERALL' },
    { id: 2, unit: 'AAL' },
    { id: 3, unit: 'DDL' },
  ];
  floors = [
    { id: 1, floor: 'FLOOR-1' },
    { id: 2, floor: 'FLOOR-2' },
    { id: 3, floor: 'FLOOR-3' },
    { id: 4, floor: 'FLOOR-4' },
    { id: 5, floor: 'FLOOR-5' },
  ];
  productionUnits = [
    { id: 1, unit: 'AAL' },
    { id: 2, unit: 'DDL' },
  ];
  fobOrCmOptions = [
    { id: 1, unit: 'FOB', defaultValue: 4000000 },
    { id: 2, unit: 'CM', defaultValue: 1000000 },
  ];
  nameOfRawMaterial = [
    { id: 1, name: 'Polyester Resin' },
    { id: 2, name: 'Styrene' },
    { id: 3, name: 'Cobalt' },
    { id: 4, name: 'WAX' },
    { id: 5, name: 'MEKPO' },
    { id: 6, name: 'Aerosil Powder' },
    { id: 7, name: 'Pearl' },
  ];

  constructor(private _componentService: _ComponentService) {
    this.host = window.location.host;
  }

  AClicked(): void {
    this.aClickedEvent.emit();
  }

  modelClose(): void {
    this.modelCloseEvent.emit();
  }

  refreshAsideClick(): void {
    this.refreshAside.emit();
  }

  requiredDateFormat(date) {
    return (
      ('0' + date.getDate()).slice(-2) +
      '-' +
      ('0' + (date.getMonth() + 1)).slice(-2) +
      '-' +
      date.getFullYear()
    );
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    if (!isNaN(date.getTime())) {
      const day = date.getDate();
      const month = date.getMonth() + 1;
      const year = date.getFullYear();
      return `${day}-${month}-${year}`;
    }
    return '';
  }

  defaultImage(): string {
    return './assets/no-content.jpg';
  }

  allowedImageTypes(): string[] {
    return ['image/jpeg', 'image/gif', 'image/png', 'image/jpg'];
  }

  allowedDocumentTypes(): string[] {
    return [
      'application/pdf',
      'application/xml',
      'application/xlsx',
      'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      'application/word',
      'application/msword',
      'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
      'application/vnd.ms-excel',
      'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      'application/vnd.ms-powerpoint',
      'application/vnd.openxmlformats-officedocument.presentationml.presentation',
      'text/plain',
    ];
  }

  stringToArray(value) {
    if (value != null) {
      return value.trim().split(',').map(String);
    }
  }

  scrollTop() {
    return window.scroll(0, 0);
  }

  adminApprovalStatus(status) {
    switch (status) {
      case '0000':
        return 'DRAFT';
      case '0001':
        return 'SENT FOR APPROVAL';
      case '0002':
        return 'APPROVED';
      case '0003':
        return 'DELIVERED';
    }
  }

  approvalSuccessStatus(value) {
    switch (value) {
      case '0001':
        return 'Sent for authorization!';
      case '0002':
        return 'Authorized!';
      case '0003':
        return 'Delivered!';
      case '0004':
        return 'Approved!';
    }
  }

  approvalRejectStatus(value) {
    switch (value) {
      case '0002':
        return 'Sent back to draft!';
      case '0003':
        return 'Sent back for authorization!';
      default:
        return 'N/A';
    }
  }

  approvalStatus(value) {
    switch (value) {
      case '0000':
        return 'Draft';
      case '0001':
        return 'Pending for authorization...';
      case '0002':
        return 'Authorized';
      case '0003':
        return 'Delivered';
      case '0004':
        return 'Approved';
      default:
        return 'N/A';
    }
  }

  approvalStatusColor(value) {
    switch (value) {
      case '0000':
        return 'bg-secondary';
      case '0001':
        return 'bg-warning';
      case '0002':
        return 'bg-success';
      case '0003':
        return 'bg-info';
      case '0004':
        return 'bg-success';
      default:
        return 'N/A';
    }
  }

  currencyByValue(value) {
    switch (value) {
      case '0':
        return 'BDT';
      case '1':
        return 'USD';
      case '2':
        return 'RMB';
      case '3':
        return 'RUPEE';
    }
  }

  requisitionByValue(value) {
    switch (value) {
      case '0':
        return 'Sample Requisition';
      case '1':
        return 'Production Requisition';
    }
  }

  pageSizes() {
    return [
      { value: 10, label: 10 },
      { value: 20, label: 20 },
      { value: 30, label: 30 },
      { value: 40, label: 40 },
      { value: 50, label: 50 },
      { value: 100, label: 100 },
      { value: 'All', label: 'All' },
    ];
  }

  showImage(imagePath): string {
    let image = '';
    if (imagePath === null || imagePath === '') {
      image = this.defaultImage();
    } else {
      if (imagePath.startsWith('data:image')) {
        image = imagePath;
      } else {
        image = environment.BASE_URL + imagePath;
      }
    }
    return image;
  }

  formStatusCollection(): any {
    return Object.entries(ApprovalFlowStatusEnum).map(([key, value]) => ({
      key,
      value,
    }));
  }

  splitString(value) {
    return value.split('');
  }

  getWeeks() {
    return this.weeks;
  }

  getMonths() {
    return this.months;
  }

  getAttributes() {
    return this.attributes;
  }

  getUnits() {
    return this.units;
  }

  getProductionUnits() {
    return this.productionUnits;
  }

  getFobOrCm() {
    return this.fobOrCmOptions;
  }

  getFloors() {
    return this.floors;
  }

  getNameOfRawMaterial() {
    return this.nameOfRawMaterial;
  }

  findWeekById(id) {
    let foundWEEK = 'N/A';
    this.getWeeks().forEach((elem) => {
      if (elem?.id == id) {
        foundWEEK = elem?.week;
      }
    });
    return foundWEEK;
  }

  findMonthById(id) {
    let foundMONTH = 'N/A';
    this.getMonths().forEach((elem) => {
      if (elem?.id == id) {
        foundMONTH = elem?.month;
      }
    });
    return foundMONTH;
  }

  findUnitsById(id) {
    let foundUnit = 'N/A';
    this.getUnits().forEach((elem) => {
      if (elem?.id == id) {
        foundUnit = elem?.unit;
      }
    });
    return foundUnit;
  }

  findProductionUnitsById(id) {
    let foundProductionUnit = 'N/A';
    this.getProductionUnits().forEach((elem) => {
      if (elem?.id == id) {
        foundProductionUnit = elem?.unit;
      }
    });
    return foundProductionUnit;
  }

  findFobOrCmById(id) {
    let foundFobOrCm = 'N/A';
    this.getFobOrCm().forEach((elem) => {
      if (elem?.id == id) {
        foundFobOrCm = elem?.unit;
      }
    });
    return foundFobOrCm;
  }

  findFloorById(id) {
    let foundFLOOR = 'N/A';
    this.getFloors().forEach((elem) => {
      if (elem?.id == id) {
        foundFLOOR = elem?.floor;
      }
    });
    return foundFLOOR;
  }

  getCurrentWeekOfMonth(): number {
    const currentDate = new Date();
    const currentWeekNumber = Math.ceil(
      (currentDate.getDate() - currentDate.getDay() + 1) / 7
    );
    return currentWeekNumber;
  }

  getCurrentWeekOfYear(): number {
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();
    const firstDayOfYear = new Date(currentYear, 0, 1);
    const diffInDays = Math.round(
      (currentDate.getTime() - firstDayOfYear.getTime()) / (1000 * 60 * 60 * 24)
    );
    const currentWeek = Math.ceil(
      (diffInDays + firstDayOfYear.getDay() + 1) / 7
    );

    return currentWeek - 1; // to change the current week number
  }

  getWeeksTillCurrent(): any[] {
    const currentWeek = this.getCurrentWeekOfYear();
    const weeksToShow = this.weeks.slice(0, currentWeek);

    return weeksToShow;
  }

  getDateRangeForWeek(weekNumber: number): string[] {
    const today = new Date();
    const firstDayOfYear = new Date(today.getFullYear(), 0, 1);
    const daysOffset =
      firstDayOfYear.getDay() > 0 ? firstDayOfYear.getDay() - 1 : 6;
    const startDateOfYear = new Date(
      today.getFullYear(),
      0,
      0 + (7 - daysOffset)
    ); // To Change the start date of the week, we need to adjust the number before the plus (+) sign
    const startDateOfWeek = new Date(startDateOfYear.getTime());
    startDateOfWeek.setDate(startDateOfWeek.getDate() + 7 * (weekNumber - 1));
    const endDateOfWeek = new Date(startDateOfWeek.getTime());
    endDateOfWeek.setDate(endDateOfWeek.getDate() + 6);
    const startDateFormat =
      startDateOfWeek.getDate().toString().padStart(2, '0') +
      '/' +
      startDateOfWeek.toLocaleString('default', { month: 'short' }) +
      '/' +
      startDateOfWeek.getFullYear();
    const endDateFormat =
      endDateOfWeek.getDate().toString().padStart(2, '0') +
      '/' +
      endDateOfWeek.toLocaleString('default', { month: 'short' }) +
      '/' +
      endDateOfWeek.getFullYear();

    return [
      (startDateFormat === 'NaN/Invalid Date/NaN' ? 'none' : startDateFormat) +
        ' - ' +
        (endDateFormat === 'NaN/Invalid Date/NaN' ? 'none' : endDateFormat),
    ];
  }
}
