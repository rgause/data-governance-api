import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDBDatabase, DBDatabase } from 'app/shared/model/db-database.model';
import { DBDatabaseService } from './db-database.service';
import { IDBSource } from 'app/shared/model/db-source.model';
import { DBSourceService } from 'app/entities/db-source/db-source.service';
import { IConcern } from 'app/shared/model/concern.model';
import { ConcernService } from 'app/entities/concern/concern.service';

type SelectableEntity = IDBSource | IConcern;

@Component({
  selector: 'jhi-db-database-update',
  templateUrl: './db-database-update.component.html',
})
export class DBDatabaseUpdateComponent implements OnInit {
  isSaving = false;
  dbsources: IDBSource[] = [];
  concerns: IConcern[] = [];

  editForm = this.fb.group({
    id: [],
    databaseName: [null, []],
    source: [],
    concerns: [],
  });

  constructor(
    protected dBDatabaseService: DBDatabaseService,
    protected dBSourceService: DBSourceService,
    protected concernService: ConcernService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dBDatabase }) => {
      this.updateForm(dBDatabase);

      this.dBSourceService.query().subscribe((res: HttpResponse<IDBSource[]>) => (this.dbsources = res.body || []));

      this.concernService.query().subscribe((res: HttpResponse<IConcern[]>) => (this.concerns = res.body || []));
    });
  }

  updateForm(dBDatabase: IDBDatabase): void {
    this.editForm.patchValue({
      id: dBDatabase.id,
      databaseName: dBDatabase.databaseName,
      source: dBDatabase.source,
      concerns: dBDatabase.concerns,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dBDatabase = this.createFromForm();
    if (dBDatabase.id !== undefined) {
      this.subscribeToSaveResponse(this.dBDatabaseService.update(dBDatabase));
    } else {
      this.subscribeToSaveResponse(this.dBDatabaseService.create(dBDatabase));
    }
  }

  private createFromForm(): IDBDatabase {
    return {
      ...new DBDatabase(),
      id: this.editForm.get(['id'])!.value,
      databaseName: this.editForm.get(['databaseName'])!.value,
      source: this.editForm.get(['source'])!.value,
      concerns: this.editForm.get(['concerns'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDBDatabase>>): void {
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
