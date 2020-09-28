import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDBDatabase, DBDatabase } from 'app/shared/model/db-database.model';
import { DBDatabaseService } from './db-database.service';
import { DBDatabaseComponent } from './db-database.component';
import { DBDatabaseDetailComponent } from './db-database-detail.component';
import { DBDatabaseUpdateComponent } from './db-database-update.component';

@Injectable({ providedIn: 'root' })
export class DBDatabaseResolve implements Resolve<IDBDatabase> {
  constructor(private service: DBDatabaseService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDBDatabase> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dBDatabase: HttpResponse<DBDatabase>) => {
          if (dBDatabase.body) {
            return of(dBDatabase.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DBDatabase());
  }
}

export const dBDatabaseRoute: Routes = [
  {
    path: '',
    component: DBDatabaseComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'DBDatabases',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DBDatabaseDetailComponent,
    resolve: {
      dBDatabase: DBDatabaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBDatabases',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DBDatabaseUpdateComponent,
    resolve: {
      dBDatabase: DBDatabaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBDatabases',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DBDatabaseUpdateComponent,
    resolve: {
      dBDatabase: DBDatabaseResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBDatabases',
    },
    canActivate: [UserRouteAccessService],
  },
];
