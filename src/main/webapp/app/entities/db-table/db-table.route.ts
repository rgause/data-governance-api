import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDBTable, DBTable } from 'app/shared/model/db-table.model';
import { DBTableService } from './db-table.service';
import { DBTableComponent } from './db-table.component';
import { DBTableDetailComponent } from './db-table-detail.component';
import { DBTableUpdateComponent } from './db-table-update.component';

@Injectable({ providedIn: 'root' })
export class DBTableResolve implements Resolve<IDBTable> {
  constructor(private service: DBTableService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDBTable> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dBTable: HttpResponse<DBTable>) => {
          if (dBTable.body) {
            return of(dBTable.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DBTable());
  }
}

export const dBTableRoute: Routes = [
  {
    path: '',
    component: DBTableComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'DBTables',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DBTableDetailComponent,
    resolve: {
      dBTable: DBTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBTables',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DBTableUpdateComponent,
    resolve: {
      dBTable: DBTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBTables',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DBTableUpdateComponent,
    resolve: {
      dBTable: DBTableResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBTables',
    },
    canActivate: [UserRouteAccessService],
  },
];
