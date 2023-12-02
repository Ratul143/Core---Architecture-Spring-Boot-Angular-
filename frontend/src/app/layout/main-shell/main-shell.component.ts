import { MainShellService } from './main-shell.service';
import {
  AfterViewChecked,
  ChangeDetectorRef,
  Component,
  OnInit,
} from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { NotificationService } from 'src/app/core/services/notification.service';
import { LoaderService } from 'src/app/core/services/loader.service';
import { CommonService } from 'src/app/core/services/common.service';
import { ApprovalFlowStatusEnum } from 'src/app/features/settings/approval-flow/enum/approval-flow-status-enum';
import { ChangePasswordComponent } from 'src/app/features/user-management/change-password/change-password.component';
import { MenuContainerObject } from './menu-container-object';
import { RoleBasedPermissionService } from 'src/app/features/module-management/role-based-permission/role-based-permission.service';
import { SIDENAV_ELEMENTS_KEY } from 'src/app/core/constants/constants';

@Component({
  selector: 'app-main-shell',
  templateUrl: './main-shell.component.html',
  styleUrls: ['./main-shell.component.css'],
})
export class MainShellComponent implements OnInit, AfterViewChecked {
  isCollapsed: boolean = false;
  draft: string;
  approved: string;
  permissionCollection: MenuContainerObject;
  defaultImg = './assets/logo/dal_logo_32x32.png';
  profileImg = './assets/logo/dal_icon.png';

  constructor(
    public authService: AuthService,
    private router: Router,
    private notificationService: NotificationService,
    public loaderService: LoaderService,
    private cdr: ChangeDetectorRef,
    private mainShellService: MainShellService,
    public commonService: CommonService,
    public roleBasePermissionService: RoleBasedPermissionService,
    private dialog: MatDialog
  ) {
    this.draft = ApprovalFlowStatusEnum.DRAFT;
    this.approved = ApprovalFlowStatusEnum.APPROVED;

    this.commonService.refreshAside.subscribe((data: string) => {
      this.findAllPermissiveModulesAndComponents();
    });
    this.findAllPermissiveModulesAndComponents();
  }

  ngOnInit(): void {
    window.scroll(0, 0);
  }

  ngAfterViewChecked() {
    this.cdr.detectChanges();
  }

  logout() {
    this.authService.logOut();
    this.router.navigateByUrl('/login');
    this.notificationService.default("You've logged out to the system");
  }

  findAllPermissiveModulesAndComponents() {
    this.roleBasePermissionService.findAllSidenavElements().subscribe({
      next: (response: MenuContainerObject) => {
        localStorage.setItem(
          SIDENAV_ELEMENTS_KEY,
          btoa(JSON.stringify(response?.permissions))
        );
        this.permissionCollection = response;
      },
    });
  }

  changePassword() {
    this.dialog.open(ChangePasswordComponent, {
      width: '600px',
    });
  }
}
