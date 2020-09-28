export interface IDBFamily {
  id?: number;
  name?: string;
}

export class DBFamily implements IDBFamily {
  constructor(public id?: number, public name?: string) {}
}
