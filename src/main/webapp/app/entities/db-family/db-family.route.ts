import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDBFamily, DBFamily } from 'app/shared/model/db-family.model';
import { DBFamilyService } from './db-family.service';
import { DBFamilyComponent } from './db-family.component';
import { DBFamilyDetailComponent } from './db-family-detail.component';
import { DBFamilyUpdateComponent } from './db-family-update.component';

@Injectable({ providedIn: 'root' })
export class DBFamilyResolve implements Resolve<IDBFamily> {
  constructor(private service: DBFamilyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDBFamily> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dBFamily: HttpResponse<DBFamily>) => {
          if (dBFamily.body) {
            return of(dBFamily.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DBFamily());
  }
}

export const dBFamilyRoute: Routes = [
  {
    path: '',
    component: DBFamilyComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'DBFamilies',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DBFamilyDetailComponent,
    resolve: {
      dBFamily: DBFamilyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBFamilies',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DBFamilyUpdateComponent,
    resolve: {
      dBFamily: DBFamilyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBFamilies',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DBFamilyUpdateComponent,
    resolve: {
      dBFamily: DBFamilyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBFamilies',
    },
    canActivate: [UserRouteAccessService],
  },
];
