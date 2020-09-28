import { IDBTable } from 'app/shared/model/db-table.model';
import { IConcern } from 'app/shared/model/concern.model';
import { IPersona } from 'app/shared/model/persona.model';

export interface IDBColumn {
  id?: number;
  columnName?: string;
  table?: IDBTable;
  concerns?: IConcern[];
  personas?: IPersona[];
}

export class DBColumn implements IDBColumn {
  constructor(
    public id?: number,
    public columnName?: string,
    public table?: IDBTable,
    public concerns?: IConcern[],
    public personas?: IPersona[]
  ) {}
}
