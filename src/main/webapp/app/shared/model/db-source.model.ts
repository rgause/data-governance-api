import { IDBFamily } from 'app/shared/model/db-family.model';
import { IConcern } from 'app/shared/model/concern.model';

export interface IDBSource {
  id?: number;
  sourceName?: string;
  family?: IDBFamily;
  concerns?: IConcern[];
}

export class DBSource implements IDBSource {
  constructor(public id?: number, public sourceName?: string, public family?: IDBFamily, public concerns?: IConcern[]) {}
}
