import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConcernType } from 'app/shared/model/concern-type.model';

type EntityResponseType = HttpResponse<IConcernType>;
type EntityArrayResponseType = HttpResponse<IConcernType[]>;

@Injectable({ providedIn: 'root' })
export class ConcernTypeService {
  public resourceUrl = SERVER_API_URL + 'api/concern-types';

  constructor(protected http: HttpClient) {}

  create(concernType: IConcernType): Observable<EntityResponseType> {
    return this.http.post<IConcernType>(this.resourceUrl, concernType, { observe: 'response' });
  }

  update(concernType: IConcernType): Observable<EntityResponseType> {
    return this.http.put<IConcernType>(this.resourceUrl, concernType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConcernType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConcernType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
