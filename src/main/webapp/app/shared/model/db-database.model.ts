import { IDBSource } from 'app/shared/model/db-source.model';
import { IConcern } from 'app/shared/model/concern.model';

export interface IDBDatabase {
  id?: number;
  databaseName?: string;
  source?: IDBSource;
  concerns?: IConcern[];
}

export class DBDatabase implements IDBDatabase {
  constructor(public id?: number, public databaseName?: string, public source?: IDBSource, public concerns?: IConcern[]) {}
}
