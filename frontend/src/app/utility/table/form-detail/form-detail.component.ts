import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormDetail } from '../../utils/form';

@Component({
  selector: 'app-form-detail',
  templateUrl: './form-detail.component.html',
  styleUrls: ['./form-detail.component.css'],
})
export class FormDetailComponent implements OnInit {
  @Input() title: string;
  @Input() details: FormDetail[];
  @Input() hasTopButton: boolean;
  @Input() topButtonText: string = 'My Button';
  @Input() topButtonIcon: string = '';

  @Input() hasUpdateButton: boolean = false;
  @Input() hasImageShowButton: boolean = false;

  @Output() topButtonClick: EventEmitter<any> = new EventEmitter();
  @Output() editButtonClick: EventEmitter<any> = new EventEmitter();
  @Output() imageShowButtonClick: EventEmitter<any> = new EventEmitter();

  public currentLanguage: string;

  constructor() {}

  ngOnInit(): void {}

  onClick() {
    this.topButtonClick.emit();
  }

  onClickUpdate() {
    this.editButtonClick.emit();
  }

  onClickImageShow() {
    this.imageShowButtonClick.emit();
  }
}
