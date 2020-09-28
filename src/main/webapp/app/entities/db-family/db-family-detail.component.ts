import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDBFamily } from 'app/shared/model/db-family.model';

@Component({
  selector: 'jhi-db-family-detail',
  templateUrl: './db-family-detail.component.html',
})
export class DBFamilyDetailComponent implements OnInit {
  dBFamily: IDBFamily | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dBFamily }) => (this.dBFamily = dBFamily));
  }

  previousState(): void {
    window.history.back();
  }
}
