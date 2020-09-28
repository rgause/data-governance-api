import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPersona, Persona } from 'app/shared/model/persona.model';
import { PersonaService } from './persona.service';
import { IDBColumn } from 'app/shared/model/db-column.model';
import { DBColumnService } from 'app/entities/db-column/db-column.service';

@Component({
  selector: 'jhi-persona-update',
  templateUrl: './persona-update.component.html',
})
export class PersonaUpdateComponent implements OnInit {
  isSaving = false;
  dbcolumns: IDBColumn[] = [];

  editForm = this.fb.group({
    id: [],
    personaName: [null, []],
    securityGroupName: [],
    columns: [],
  });

  constructor(
    protected personaService: PersonaService,
    protected dBColumnService: DBColumnService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ persona }) => {
      this.updateForm(persona);

      this.dBColumnService.query().subscribe((res: HttpResponse<IDBColumn[]>) => (this.dbcolumns = res.body || []));
    });
  }

  updateForm(persona: IPersona): void {
    this.editForm.patchValue({
      id: persona.id,
      personaName: persona.personaName,
      securityGroupName: persona.securityGroupName,
      columns: persona.columns,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const persona = this.createFromForm();
    if (persona.id !== undefined) {
      this.subscribeToSaveResponse(this.personaService.update(persona));
    } else {
      this.subscribeToSaveResponse(this.personaService.create(persona));
    }
  }

  private createFromForm(): IPersona {
    return {
      ...new Persona(),
      id: this.editForm.get(['id'])!.value,
      personaName: this.editForm.get(['personaName'])!.value,
      securityGroupName: this.editForm.get(['securityGroupName'])!.value,
      columns: this.editForm.get(['columns'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersona>>): void {
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

  trackById(index: number, item: IDBColumn): any {
    return item.id;
  }

  getSelected(selectedVals: IDBColumn[], option: IDBColumn): IDBColumn {
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
