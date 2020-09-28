import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { DBColumnUpdateComponent } from 'app/entities/db-column/db-column-update.component';
import { DBColumnService } from 'app/entities/db-column/db-column.service';
import { DBColumn } from 'app/shared/model/db-column.model';

describe('Component Tests', () => {
  describe('DBColumn Management Update Component', () => {
    let comp: DBColumnUpdateComponent;
    let fixture: ComponentFixture<DBColumnUpdateComponent>;
    let service: DBColumnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [DBColumnUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DBColumnUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DBColumnUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DBColumnService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DBColumn(123);
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
        const entity = new DBColumn();
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
