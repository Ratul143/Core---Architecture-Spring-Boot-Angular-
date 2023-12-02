import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class SharedService {

    constructor(private http: HttpClient) { }

    public disableSource = new BehaviorSubject<any>(undefined);

    private currentLoggedInUserID = '';
    private currentUserCompanyOid = '';
    private currentUserCompanyType = '';
    private currentUserRole = '';
    private userLoginOid = '';
    private userExtension = '';

    disableHeaderSidebar(disable: boolean) {
        this.disableSource.next(disable);
    }

    getUserLoginOid(){
        return this.userLoginOid;
    }

    setUserLoginOid(id: string){
        this.userLoginOid = id;
    }

    getcurrentLoggedInUserID(){
        return this.currentLoggedInUserID;
    }

    setcurrentLoggedInUserID(id: string){
        this.currentLoggedInUserID = id;
    }
    
    setCurrentUserCompanyOid(oid: string){
        this.currentUserCompanyOid = oid;
    }

    getCurrentUserCompanyOid(){
        return this.currentUserCompanyOid;
    }

    setCurrentUserCompanyType(type: string){
        this.currentUserCompanyType = type;
    }

    getCurrentUserCompanyType(){
        return this.currentUserCompanyType;
    }

    setCurrentUserRole(role: string){
        this.currentUserRole = role;
    }

    getCurrentUserRole(){
        return this.currentUserRole;
    }

    setUserExtension(userExtension: string){
        this.userExtension = userExtension;
    }

    getUserExtension(){
        return this.userExtension;
    }

}
