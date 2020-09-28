import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDBSource, DBSource } from 'app/shared/model/db-source.model';
import { DBSourceService } from './db-source.service';
import { IDBFamily } from 'app/shared/model/db-family.model';
import { DBFamilyService } from 'app/entities/db-family/db-family.service';
import { IConcern } from 'app/shared/model/concern.model';
import { ConcernService } from 'app/entities/concern/concern.service';

type SelectableEntity = IDBFamily | IConcern;

@Component({
  selector: 'jhi-db-source-update',
  templateUrl: './db-source-update.component.html',
})
export class DBSourceUpdateComponent implements OnInit {
  isSaving = false;
  dbfamilies: IDBFamily[] = [];
  concerns: IConcern[] = [];

  editForm = this.fb.group({
    id: [],
    sourceName: [null, []],
    family: [],
    concerns: [],
  });

  constructor(
    protected dBSourceService: DBSourceService,
    protected dBFamilyService: DBFamilyService,
    protected concernService: ConcernService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dBSource }) => {
      this.updateForm(dBSource);

      this.dBFamilyService.query().subscribe((res: HttpResponse<IDBFamily[]>) => (this.dbfamilies = res.body || []));

      this.concernService.query().subscribe((res: HttpResponse<IConcern[]>) => (this.concerns = res.body || []));
    });
  }

  updateForm(dBSource: IDBSource): void {
    this.editForm.patchValue({
      id: dBSource.id,
      sourceName: dBSource.sourceName,
      family: dBSource.family,
      concerns: dBSource.concerns,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dBSource = this.createFromForm();
    if (dBSource.id !== undefined) {
      this.subscribeToSaveResponse(this.dBSourceService.update(dBSource));
    } else {
      this.subscribeToSaveResponse(this.dBSourceService.create(dBSource));
    }
  }

  private createFromForm(): IDBSource {
    return {
      ...new DBSource(),
      id: this.editForm.get(['id'])!.value,
      sourceName: this.editForm.get(['sourceName'])!.value,
      family: this.editForm.get(['family'])!.value,
      concerns: this.editForm.get(['concerns'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDBSource>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IConcern[], option: IConcern): IConcern {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
