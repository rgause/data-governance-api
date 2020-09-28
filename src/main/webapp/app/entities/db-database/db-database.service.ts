import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDBDatabase } from 'app/shared/model/db-database.model';

type EntityResponseType = HttpResponse<IDBDatabase>;
type EntityArrayResponseType = HttpResponse<IDBDatabase[]>;

@Injectable({ providedIn: 'root' })
export class DBDatabaseService {
  public resourceUrl = SERVER_API_URL + 'api/db-databases';

  constructor(protected http: HttpClient) {}

  create(dBDatabase: IDBDatabase): Observable<EntityResponseType> {
    return this.http.post<IDBDatabase>(this.resourceUrl, dBDatabase, { observe: 'response' });
  }

  update(dBDatabase: IDBDatabase): Observable<EntityResponseType> {
    return this.http.put<IDBDatabase>(this.resourceUrl, dBDatabase, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDBDatabase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDBDatabase[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
