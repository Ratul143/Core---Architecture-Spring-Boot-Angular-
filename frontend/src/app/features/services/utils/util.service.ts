import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  private readonly digits = ['০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯'];
  private engDigits = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];


  private monthEn = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  private monthBn = ['জানুয়ারি', 'ফেব্রুয়ারি', 'মার্চ', 'এপ্রিল', 'মে', 'জুন', 'জুলাই', 'আগস্ট', 'সেপ্টেম্বর', 'অক্টোবর', 'নভেম্বর', 'ডিসেম্বর'];


  translateDate(dateStr: string) {
    if(!dateStr){
      return '';
    }
    let fdate = '';
    for (const character of dateStr) {
      fdate += this.engDigits.includes(character) ?  this.digits[+character] : character ;
    }
    return fdate;
  }

  translateMonth(month: string) {
    return this.monthEn.includes(month) ? this.monthBn[this.monthEn.indexOf(month)]: '';
  }

}
