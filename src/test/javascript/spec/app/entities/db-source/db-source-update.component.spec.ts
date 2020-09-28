import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { DBSourceUpdateComponent } from 'app/entities/db-source/db-source-update.component';
import { DBSourceService } from 'app/entities/db-source/db-source.service';
import { DBSource } from 'app/shared/model/db-source.model';

describe('Component Tests', () => {
  describe('DBSource Management Update Component', () => {
    let comp: DBSourceUpdateComponent;
    let fixture: ComponentFixture<DBSourceUpdateComponent>;
    let service: DBSourceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [DBSourceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DBSourceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DBSourceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DBSourceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DBSource(123);
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
        const entity = new DBSource();
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
