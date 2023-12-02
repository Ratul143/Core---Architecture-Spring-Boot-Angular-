import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TableButtonAction, TableConsts } from '@app/utility/utils/table-action';

@Component({
  // tslint:disable-next-line: component-selector
  selector: '[action-buttons]',
  templateUrl: './action-buttons.component.html',
  styleUrls: ['./action-buttons.component.css'],
})
export class ActionButtonsComponent {
  constructor() { }

  @Input() value!: string;
  @Output() buttonAction: EventEmitter<TableButtonAction> = new EventEmitter<TableButtonAction>();

  onEditClick(): void {
    this.buttonAction.emit({
      name: TableConsts.actionButton.edit,
      value: this.value,
    });
  }
  onDeleteClick(): void {
    this.buttonAction.emit({ name: TableConsts.actionButton.delete });
  }
  onViewClick(): void {
    this.buttonAction.emit({ name: TableConsts.actionButton.view });
  }

}
