export interface IConcernType {
  id?: number;
  concernTypeName?: string;
}

export class ConcernType implements IConcernType {
  constructor(public id?: number, public concernTypeName?: string) {}
}
