import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { LoaderService } from 'src/app/core/services/loader.service';

@Component({
  selector: 'app-no-permission',
  templateUrl: './no-permission.component.html',
  styleUrls: ['./no-permission.component.css'],
})
export class NoPermissionComponent implements OnInit {
  constructor(
    public authService: AuthService,
    public loaderService: LoaderService
  ) {}

  ngOnInit(): void {}
}
