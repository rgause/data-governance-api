import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDBFamily } from 'app/shared/model/db-family.model';

type EntityResponseType = HttpResponse<IDBFamily>;
type EntityArrayResponseType = HttpResponse<IDBFamily[]>;

@Injectable({ providedIn: 'root' })
export class DBFamilyService {
  public resourceUrl = SERVER_API_URL + 'api/db-families';

  constructor(protected http: HttpClient) {}

  create(dBFamily: IDBFamily): Observable<EntityResponseType> {
    return this.http.post<IDBFamily>(this.resourceUrl, dBFamily, { observe: 'response' });
  }

  update(dBFamily: IDBFamily): Observable<EntityResponseType> {
    return this.http.put<IDBFamily>(this.resourceUrl, dBFamily, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDBFamily>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDBFamily[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
