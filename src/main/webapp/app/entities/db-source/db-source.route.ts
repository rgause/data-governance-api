import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDBSource, DBSource } from 'app/shared/model/db-source.model';
import { DBSourceService } from './db-source.service';
import { DBSourceComponent } from './db-source.component';
import { DBSourceDetailComponent } from './db-source-detail.component';
import { DBSourceUpdateComponent } from './db-source-update.component';

@Injectable({ providedIn: 'root' })
export class DBSourceResolve implements Resolve<IDBSource> {
  constructor(private service: DBSourceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDBSource> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dBSource: HttpResponse<DBSource>) => {
          if (dBSource.body) {
            return of(dBSource.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DBSource());
  }
}

export const dBSourceRoute: Routes = [
  {
    path: '',
    component: DBSourceComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'DBSources',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DBSourceDetailComponent,
    resolve: {
      dBSource: DBSourceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBSources',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DBSourceUpdateComponent,
    resolve: {
      dBSource: DBSourceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBSources',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DBSourceUpdateComponent,
    resolve: {
      dBSource: DBSourceResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DBSources',
    },
    canActivate: [UserRouteAccessService],
  },
];
