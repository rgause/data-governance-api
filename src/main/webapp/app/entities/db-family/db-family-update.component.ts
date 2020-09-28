import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDBFamily, DBFamily } from 'app/shared/model/db-family.model';
import { DBFamilyService } from './db-family.service';

@Component({
  selector: 'jhi-db-family-update',
  templateUrl: './db-family-update.component.html',
})
export class DBFamilyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, []],
  });

  constructor(protected dBFamilyService: DBFamilyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dBFamily }) => {
      this.updateForm(dBFamily);
    });
  }

  updateForm(dBFamily: IDBFamily): void {
    this.editForm.patchValue({
      id: dBFamily.id,
      name: dBFamily.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dBFamily = this.createFromForm();
    if (dBFamily.id !== undefined) {
      this.subscribeToSaveResponse(this.dBFamilyService.update(dBFamily));
    } else {
      this.subscribeToSaveResponse(this.dBFamilyService.create(dBFamily));
    }
  }

  private createFromForm(): IDBFamily {
    return {
      ...new DBFamily(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDBFamily>>): void {
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
