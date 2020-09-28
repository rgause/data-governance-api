import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConcern, Concern } from 'app/shared/model/concern.model';
import { ConcernService } from './concern.service';
import { ConcernComponent } from './concern.component';
import { ConcernDetailComponent } from './concern-detail.component';
import { ConcernUpdateComponent } from './concern-update.component';

@Injectable({ providedIn: 'root' })
export class ConcernResolve implements Resolve<IConcern> {
  constructor(private service: ConcernService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConcern> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((concern: HttpResponse<Concern>) => {
          if (concern.body) {
            return of(concern.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Concern());
  }
}

export const concernRoute: Routes = [
  {
    path: '',
    component: ConcernComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Concerns',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConcernDetailComponent,
    resolve: {
      concern: ConcernResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Concerns',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConcernUpdateComponent,
    resolve: {
      concern: ConcernResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Concerns',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConcernUpdateComponent,
    resolve: {
      concern: ConcernResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Concerns',
    },
    canActivate: [UserRouteAccessService],
  },
];
