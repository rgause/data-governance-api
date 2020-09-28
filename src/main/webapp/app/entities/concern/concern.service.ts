import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConcern } from 'app/shared/model/concern.model';

type EntityResponseType = HttpResponse<IConcern>;
type EntityArrayResponseType = HttpResponse<IConcern[]>;

@Injectable({ providedIn: 'root' })
export class ConcernService {
  public resourceUrl = SERVER_API_URL + 'api/concerns';

  constructor(protected http: HttpClient) {}

  create(concern: IConcern): Observable<EntityResponseType> {
    return this.http.post<IConcern>(this.resourceUrl, concern, { observe: 'response' });
  }

  update(concern: IConcern): Observable<EntityResponseType> {
    return this.http.put<IConcern>(this.resourceUrl, concern, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConcern>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConcern[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
