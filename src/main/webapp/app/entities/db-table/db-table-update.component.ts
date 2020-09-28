import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDBTable, DBTable } from 'app/shared/model/db-table.model';
import { DBTableService } from './db-table.service';
import { IDBDatabase } from 'app/shared/model/db-database.model';
import { DBDatabaseService } from 'app/entities/db-database/db-database.service';
import { IConcern } from 'app/shared/model/concern.model';
import { ConcernService } from 'app/entities/concern/concern.service';

type SelectableEntity = IDBDatabase | IConcern;

@Component({
  selector: 'jhi-db-table-update',
  templateUrl: './db-table-update.component.html',
})
export class DBTableUpdateComponent implements OnInit {
  isSaving = false;
  dbdatabases: IDBDatabase[] = [];
  concerns: IConcern[] = [];

  editForm = this.fb.group({
    id: [],
    tableName: [null, []],
    database: [],
    concerns: [],
  });

  constructor(
    protected dBTableService: DBTableService,
    protected dBDatabaseService: DBDatabaseService,
    protected concernService: ConcernService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dBTable }) => {
      this.updateForm(dBTable);

      this.dBDatabaseService.query().subscribe((res: HttpResponse<IDBDatabase[]>) => (this.dbdatabases = res.body || []));

      this.concernService.query().subscribe((res: HttpResponse<IConcern[]>) => (this.concerns = res.body || []));
    });
  }

  updateForm(dBTable: IDBTable): void {
    this.editForm.patchValue({
      id: dBTable.id,
      tableName: dBTable.tableName,
      database: dBTable.database,
      concerns: dBTable.concerns,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dBTable = this.createFromForm();
    if (dBTable.id !== undefined) {
      this.subscribeToSaveResponse(this.dBTableService.update(dBTable));
    } else {
      this.subscribeToSaveResponse(this.dBTableService.create(dBTable));
    }
  }

  private createFromForm(): IDBTable {
    return {
      ...new DBTable(),
      id: this.editForm.get(['id'])!.value,
      tableName: this.editForm.get(['tableName'])!.value,
      database: this.editForm.get(['database'])!.value,
      concerns: this.editForm.get(['concerns'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDBTable>>): void {
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
