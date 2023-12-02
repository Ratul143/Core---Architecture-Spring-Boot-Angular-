import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { RecentVisitorsService } from './recent-visitors.service';
import { ActivatedRoute } from '@angular/router';
import { RecentVisitors } from 'src/app/core/classes/RecentVisitors';
import { CommonService } from 'src/app/core/services/common.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-rm-group',
  templateUrl: './recent-visitors.component.html',
  styleUrls: ['./recent-visitors.component.css'],
})
export class RecentVisitorsComponent implements OnInit {
  rmGroupGroup: FormGroup;
  update: boolean;
  userId: number;
  searchValue: string = '';
  pageOffset = 1;
  itemsPerPage = 10;
  totalItems = 0;
  recentVisitorsCollection: RecentVisitors[];

  constructor(
    public commonService: CommonService,
    public authService: AuthService,
    public dialog: MatDialog,
    private recentVisitorsService: RecentVisitorsService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.commonService.scrollTop();
    this.commonService.aClickedEvent.subscribe((data: string) => {
      this.getRecentVisitorsList();
    });
    this.getRecentVisitorsList();
  }

  permission(type: string): boolean {
    return this.authService.permission(environment.visitors.substring(1), type);
  }

  getRecentVisitorsList() {
    this.recentVisitorsService
      .recentVisitorsList(this.searchValue, this.pageOffset, this.itemsPerPage)
      .subscribe({
        next: (response: RecentVisitors[]) => {
          this.recentVisitorsCollection = response;
        },
        error: (errorResponse: HttpErrorResponse) => {
          const errors = errorResponse.error;
        },
      });
    this.count();
  }

  resetForm() {
    this.rmGroupGroup.reset();
    this.update = false;
  }

  edit(event) {
    window.scroll(0, 0);
    this.update = true;
    this.userId = event.id;
    this.rmGroupGroup.setValue({
      rmGroup: event.rmGroup,
    });
  }

  search(event) {
    this.searchValue = event;
    this.getRecentVisitorsList();
  }

  itemsOnChange(event) {
    this.pageOffset = 1;
    this.itemsPerPage = event;
    this.getRecentVisitorsList();
  }

  pageChange(newPage: number) {
    this.pageOffset = newPage;
    this.getRecentVisitorsList();
  }

  count() {
    this.recentVisitorsService.count().subscribe({
      next: (response: any) => {
        this.totalItems = response;
      },
    });
  }

  getSL(i) {
    return Number(this.itemsPerPage) * (Number(this.pageOffset) - 1) + i;
  }
}
