import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDBColumn, DBColumn } from 'app/shared/model/db-column.model';
import { DBColumnService } from './db-column.service';
import { DBColumnComponent } from './db-column.component';
import { DBColumnDetailComponent } from './db-column-detail.component';
import { DBColumnUpdateComponent } from './db-column-update.component';

@Injectable({ providedIn: 'root' })
export class DBColumnResolve implements Resolve<IDBColumn> {
  constructor(private service: DBColumnService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDBColumn> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dBColumn: HttpResponse<DBColumn>) => {
          if (dBColumn.body) {
            return of(dBColumn.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DBColumn());
  }
}

export const dBColumnRoute: Routes = [
  {
    path: '',
    component: DBColumnComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'DBColumns',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DBColumnDetailComponent,
    resolve: {
      dBColumn: DBColumnResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBColumns',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DBColumnUpdateComponent,
    resolve: {
      dBColumn: DBColumnResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBColumns',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DBColumnUpdateComponent,
    resolve: {
      dBColumn: DBColumnResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBColumns',
    },
    canActivate: [UserRouteAccessService],
  },
];
