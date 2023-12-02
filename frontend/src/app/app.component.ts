import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor(private auth: AuthService, private router: Router) {
    window.scroll(0, 0);
    this.checkIfUserIsLoggedIn();
  }

  checkIfUserIsLoggedIn() {
    if (!this.auth.isUserLoggedIn()) {
      this.router.navigateByUrl('/login');
    }
  }
}
