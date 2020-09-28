import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDBSource } from 'app/shared/model/db-source.model';

@Component({
  selector: 'jhi-db-source-detail',
  templateUrl: './db-source-detail.component.html',
})
export class DBSourceDetailComponent implements OnInit {
  dBSource: IDBSource | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dBSource }) => (this.dBSource = dBSource));
  }

  previousState(): void {
    window.history.back();
  }
}
