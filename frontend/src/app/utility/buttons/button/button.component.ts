import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
export type ButtonType = 'button' | 'submit';
export type ButtonColor = 'basic' | 'primary' | 'danger' | 'warn' | 'accent';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss']
})
export class ButtonComponent implements OnInit {

  @Input() type: ButtonType;
  @Input() color: ButtonColor;
  @Input() isDisabled: boolean;

  @Output() myclick = new EventEmitter<any>();
  
  constructor() { }

  ngOnInit(): void {
    
  }

  onClick(): void {
    this.myclick.emit();
}

}
