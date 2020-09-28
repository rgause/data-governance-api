import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDBTable } from 'app/shared/model/db-table.model';

type EntityResponseType = HttpResponse<IDBTable>;
type EntityArrayResponseType = HttpResponse<IDBTable[]>;

@Injectable({ providedIn: 'root' })
export class DBTableService {
  public resourceUrl = SERVER_API_URL + 'api/db-tables';

  constructor(protected http: HttpClient) {}

  create(dBTable: IDBTable): Observable<EntityResponseType> {
    return this.http.post<IDBTable>(this.resourceUrl, dBTable, { observe: 'response' });
  }

  update(dBTable: IDBTable): Observable<EntityResponseType> {
    return this.http.put<IDBTable>(this.resourceUrl, dBTable, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDBTable>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDBTable[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
