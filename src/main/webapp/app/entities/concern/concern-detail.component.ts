import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConcern } from 'app/shared/model/concern.model';

@Component({
  selector: 'jhi-concern-detail',
  templateUrl: './concern-detail.component.html',
})
export class ConcernDetailComponent implements OnInit {
  concern: IConcern | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concern }) => (this.concern = concern));
  }

  previousState(): void {
    window.history.back();
  }
}
