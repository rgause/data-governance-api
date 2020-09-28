import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConcernType, ConcernType } from 'app/shared/model/concern-type.model';
import { ConcernTypeService } from './concern-type.service';
import { ConcernTypeComponent } from './concern-type.component';
import { ConcernTypeDetailComponent } from './concern-type-detail.component';
import { ConcernTypeUpdateComponent } from './concern-type-update.component';

@Injectable({ providedIn: 'root' })
export class ConcernTypeResolve implements Resolve<IConcernType> {
  constructor(private service: ConcernTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConcernType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((concernType: HttpResponse<ConcernType>) => {
          if (concernType.body) {
            return of(concernType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConcernType());
  }
}

export const concernTypeRoute: Routes = [
  {
    path: '',
    component: ConcernTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ConcernTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConcernTypeDetailComponent,
    resolve: {
      concernType: ConcernTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConcernTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConcernTypeUpdateComponent,
    resolve: {
      concernType: ConcernTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConcernTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConcernTypeUpdateComponent,
    resolve: {
      concernType: ConcernTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConcernTypes',
    },
    canActivate: [UserRouteAccessService],
  },
];
