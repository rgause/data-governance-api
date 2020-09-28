import { IConcernType } from 'app/shared/model/concern-type.model';
import { IDBColumn } from 'app/shared/model/db-column.model';
import { IDBTable } from 'app/shared/model/db-table.model';
import { IDBDatabase } from 'app/shared/model/db-database.model';
import { IDBSource } from 'app/shared/model/db-source.model';

export interface IConcern {
  id?: number;
  concernDescription?: string;
  concernType?: IConcernType;
  columns?: IDBColumn[];
  tables?: IDBTable[];
  databases?: IDBDatabase[];
  sources?: IDBSource[];
}

export class Concern implements IConcern {
  constructor(
    public id?: number,
    public concernDescription?: string,
    public concernType?: IConcernType,
    public columns?: IDBColumn[],
    public tables?: IDBTable[],
    public databases?: IDBDatabase[],
    public sources?: IDBSource[]
  ) {}
}
