import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { DBTableUpdateComponent } from 'app/entities/db-table/db-table-update.component';
import { DBTableService } from 'app/entities/db-table/db-table.service';
import { DBTable } from 'app/shared/model/db-table.model';

describe('Component Tests', () => {
  describe('DBTable Management Update Component', () => {
    let comp: DBTableUpdateComponent;
    let fixture: ComponentFixture<DBTableUpdateComponent>;
    let service: DBTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [DBTableUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DBTableUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DBTableUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DBTableService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DBTable(123);
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
        const entity = new DBTable();
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
