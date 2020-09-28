import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { DBDatabaseUpdateComponent } from 'app/entities/db-database/db-database-update.component';
import { DBDatabaseService } from 'app/entities/db-database/db-database.service';
import { DBDatabase } from 'app/shared/model/db-database.model';

describe('Component Tests', () => {
  describe('DBDatabase Management Update Component', () => {
    let comp: DBDatabaseUpdateComponent;
    let fixture: ComponentFixture<DBDatabaseUpdateComponent>;
    let service: DBDatabaseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [DBDatabaseUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DBDatabaseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DBDatabaseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DBDatabaseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DBDatabase(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new DBDatabase();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
