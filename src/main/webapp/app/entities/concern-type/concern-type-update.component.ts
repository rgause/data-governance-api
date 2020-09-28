import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConcernType, ConcernType } from 'app/shared/model/concern-type.model';
import { ConcernTypeService } from './concern-type.service';

@Component({
  selector: 'jhi-concern-type-update',
  templateUrl: './concern-type-update.component.html',
})
export class ConcernTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    concernTypeName: [null, []],
  });

  constructor(protected concernTypeService: ConcernTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concernType }) => {
      this.updateForm(concernType);
    });
  }

  updateForm(concernType: IConcernType): void {
    this.editForm.patchValue({
      id: concernType.id,
      concernTypeName: concernType.concernTypeName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const concernType = this.createFromForm();
    if (concernType.id !== undefined) {
      this.subscribeToSaveResponse(this.concernTypeService.update(concernType));
    } else {
      this.subscribeToSaveResponse(this.concernTypeService.create(concernType));
    }
  }

  private createFromForm(): IConcernType {
    return {
      ...new ConcernType(),
      id: this.editForm.get(['id'])!.value,
      concernTypeName: this.editForm.get(['concernTypeName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConcernType>>): void {
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
}
