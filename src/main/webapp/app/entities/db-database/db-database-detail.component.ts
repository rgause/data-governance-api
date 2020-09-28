import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDBDatabase } from 'app/shared/model/db-database.model';

@Component({
  selector: 'jhi-db-database-detail',
  templateUrl: './db-database-detail.component.html',
})
export class DBDatabaseDetailComponent implements OnInit {
  dBDatabase: IDBDatabase | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dBDatabase }) => (this.dBDatabase = dBDatabase));
  }

  previousState(): void {
    window.history.back();
  }
}
