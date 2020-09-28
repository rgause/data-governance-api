import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDBColumn } from 'app/shared/model/db-column.model';

@Component({
  selector: 'jhi-db-column-detail',
  templateUrl: './db-column-detail.component.html',
})
export class DBColumnDetailComponent implements OnInit {
  dBColumn: IDBColumn | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dBColumn }) => (this.dBColumn = dBColumn));
  }

  previousState(): void {
    window.history.back();
  }
}
