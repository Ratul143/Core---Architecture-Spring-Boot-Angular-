import { SelectionModel } from '@angular/cdk/collections';
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { TableButtonAction } from '../../utils/table-action';
import { TableColumn } from '../../utils';
import { IButtonDescription } from '../../utils/button-description';
import { UtilService } from 'src/app/features/services/utils/util.service';

@Component({
  selector: 'app-table-except-pages',
  templateUrl: './table-except-pages.component.html',
  styleUrls: ['./table-except-pages.component.css'],
})
export class TableExceptPagesComponent implements OnInit {
  @Output() action: EventEmitter<TableButtonAction> =
    new EventEmitter<TableButtonAction>();
  @Output() pageSortEvent: EventEmitter<Sort> = new EventEmitter<Sort>();
  @Input() columns!: Array<TableColumn>;
  @Input() dataset: Array<any> = [];
  @Input() totalCount = 0;
  @Input() isSelectColumnVisible!: boolean;
  @Input() isActionColumnVisible!: boolean;
  @ViewChild(MatSort) sort!: MatSort;
  dataSource!: MatTableDataSource<any>;
  selection = new SelectionModel<any>(true, []);
  displayedColumns: string[] = [];
  value = new FormControl();

  public currentLanguage: string;

  @Input() listButton: IButtonDescription[] = [];

  constructor(private utilService: UtilService) {}

  ngOnInit(): void {
    this.displayedColumns.push('index');
    // set checkbox column
    this.isSelectColumnVisible ? this.displayedColumns.push('select') : '';

    // set table columns
    this.displayedColumns = this.displayedColumns.concat(
      this.columns.map((x) => x.columnDef)
    ); // pre-fix static

    // add action column
    this.listButton.length > 0 ? this.displayedColumns.push('action') : '';
    this.dataSource = new MatTableDataSource<any>(this.dataset);

    // set pagination
  }

  onTableAction(e: TableButtonAction): void {
    this.action.emit(e);
  }
  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected(): boolean {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle(): void {
    this.isAllSelected()
      ? this.selection.clear()
      : this.dataSource.data.forEach((row) => this.selection.select(row));
  }
  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
  }

  sortChange(event: Sort): void {
    this.pageSortEvent.emit({
      active: event.active,
      direction: event.direction,
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  announceClick(listener: (data?: any) => any, index: number) {
    const datum = this.dataset[index];
    listener(datum);
  }

  translateNumber(num: any) {
    return this.utilService.translateDate(num + '');
  }
}
