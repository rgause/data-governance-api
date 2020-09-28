import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConcernType } from 'app/shared/model/concern-type.model';

@Component({
  selector: 'jhi-concern-type-detail',
  templateUrl: './concern-type-detail.component.html',
})
export class ConcernTypeDetailComponent implements OnInit {
  concernType: IConcernType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concernType }) => (this.concernType = concernType));
  }

  previousState(): void {
    window.history.back();
  }
}
