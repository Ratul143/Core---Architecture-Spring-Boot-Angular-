import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-session-expired',
    templateUrl: './session-expired.component.html',
    styleUrls: ['./session-expired.component.css']
})
export class SessionExpiredComponent implements OnInit {

    public isLoading: boolean = false;

    constructor(private router: Router) { }

    ngOnInit(): void {
        // if (!this.oAuth2Service.oAuth2.isAccessTokenExpired()) {
        //     this.isLoading = true;
        //     this.router.navigate(['/']);
        // }
    }

}
