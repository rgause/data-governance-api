import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDBColumn, DBColumn } from 'app/shared/model/db-column.model';
import { DBColumnService } from './db-column.service';
import { IDBTable } from 'app/shared/model/db-table.model';
import { DBTableService } from 'app/entities/db-table/db-table.service';
import { IConcern } from 'app/shared/model/concern.model';
import { ConcernService } from 'app/entities/concern/concern.service';

type SelectableEntity = IDBTable | IConcern;

@Component({
  selector: 'jhi-db-column-update',
  templateUrl: './db-column-update.component.html',
})
export class DBColumnUpdateComponent implements OnInit {
  isSaving = false;
  dbtables: IDBTable[] = [];
  concerns: IConcern[] = [];

  editForm = this.fb.group({
    id: [],
    columnName: [null, []],
    table: [],
    concerns: [],
  });

  constructor(
    protected dBColumnService: DBColumnService,
    protected dBTableService: DBTableService,
    protected concernService: ConcernService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dBColumn }) => {
      this.updateForm(dBColumn);

      this.dBTableService.query().subscribe((res: HttpResponse<IDBTable[]>) => (this.dbtables = res.body || []));

      this.concernService.query().subscribe((res: HttpResponse<IConcern[]>) => (this.concerns = res.body || []));
    });
  }

  updateForm(dBColumn: IDBColumn): void {
    this.editForm.patchValue({
      id: dBColumn.id,
      columnName: dBColumn.columnName,
      table: dBColumn.table,
      concerns: dBColumn.concerns,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dBColumn = this.createFromForm();
    if (dBColumn.id !== undefined) {
      this.subscribeToSaveResponse(this.dBColumnService.update(dBColumn));
    } else {
      this.subscribeToSaveResponse(this.dBColumnService.create(dBColumn));
    }
  }

  private createFromForm(): IDBColumn {
    return {
      ...new DBColumn(),
      id: this.editForm.get(['id'])!.value,
      columnName: this.editForm.get(['columnName'])!.value,
      table: this.editForm.get(['table'])!.value,
      concerns: this.editForm.get(['concerns'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDBColumn>>): void {
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
