import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDBColumn } from 'app/shared/model/db-column.model';

type EntityResponseType = HttpResponse<IDBColumn>;
type EntityArrayResponseType = HttpResponse<IDBColumn[]>;

@Injectable({ providedIn: 'root' })
export class DBColumnService {
  public resourceUrl = SERVER_API_URL + 'api/db-columns';

  constructor(protected http: HttpClient) {}

  create(dBColumn: IDBColumn): Observable<EntityResponseType> {
    return this.http.post<IDBColumn>(this.resourceUrl, dBColumn, { observe: 'response' });
  }

  update(dBColumn: IDBColumn): Observable<EntityResponseType> {
    return this.http.put<IDBColumn>(this.resourceUrl, dBColumn, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDBColumn>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDBColumn[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
