import { IDBColumn } from 'app/shared/model/db-column.model';

export interface IPersona {
  id?: number;
  personaName?: string;
  securityGroupName?: string;
  columns?: IDBColumn[];
}

export class Persona implements IPersona {
  constructor(public id?: number, public personaName?: string, public securityGroupName?: string, public columns?: IDBColumn[]) {}
}
