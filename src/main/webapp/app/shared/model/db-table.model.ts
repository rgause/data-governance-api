import { IDBDatabase } from 'app/shared/model/db-database.model';
import { IConcern } from 'app/shared/model/concern.model';

export interface IDBTable {
  id?: number;
  tableName?: string;
  database?: IDBDatabase;
  concerns?: IConcern[];
}

export class DBTable implements IDBTable {
  constructor(public id?: number, public tableName?: string, public database?: IDBDatabase, public concerns?: IConcern[]) {}
}
