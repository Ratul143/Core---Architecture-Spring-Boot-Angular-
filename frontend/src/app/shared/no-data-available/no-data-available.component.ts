import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { LoaderService } from 'src/app/core/services/loader.service';

@Component({
  selector: 'app-no-data-available',
  templateUrl: './no-data-available.component.html',
  styleUrls: ['./no-data-available.component.css'],
})
export class NoDataAvailableComponent implements OnInit {
  constructor(
    public authService: AuthService,
    public loaderService: LoaderService
  ) {}

  ngOnInit(): void {}
}
