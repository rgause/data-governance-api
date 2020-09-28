import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConcern, Concern } from 'app/shared/model/concern.model';
import { ConcernService } from './concern.service';
import { IConcernType } from 'app/shared/model/concern-type.model';
import { ConcernTypeService } from 'app/entities/concern-type/concern-type.service';

@Component({
  selector: 'jhi-concern-update',
  templateUrl: './concern-update.component.html',
})
export class ConcernUpdateComponent implements OnInit {
  isSaving = false;
  concerntypes: IConcernType[] = [];

  editForm = this.fb.group({
    id: [],
    concernDescription: [null, []],
    concernType: [],
  });

  constructor(
    protected concernService: ConcernService,
    protected concernTypeService: ConcernTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concern }) => {
      this.updateForm(concern);

      this.concernTypeService.query().subscribe((res: HttpResponse<IConcernType[]>) => (this.concerntypes = res.body || []));
    });
  }

  updateForm(concern: IConcern): void {
    this.editForm.patchValue({
      id: concern.id,
      concernDescription: concern.concernDescription,
      concernType: concern.concernType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const concern = this.createFromForm();
    if (concern.id !== undefined) {
      this.subscribeToSaveResponse(this.concernService.update(concern));
    } else {
      this.subscribeToSaveResponse(this.concernService.create(concern));
    }
  }

  private createFromForm(): IConcern {
    return {
      ...new Concern(),
      id: this.editForm.get(['id'])!.value,
      concernDescription: this.editForm.get(['concernDescription'])!.value,
      concernType: this.editForm.get(['concernType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConcern>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IConcernType): any {
    return item.id;
  }
}
