import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDBTable } from 'app/shared/model/db-table.model';

@Component({
  selector: 'jhi-db-table-detail',
  templateUrl: './db-table-detail.component.html',
})
export class DBTableDetailComponent implements OnInit {
  dBTable: IDBTable | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dBTable }) => (this.dBTable = dBTable));
  }

  previousState(): void {
    window.history.back();
  }
}
