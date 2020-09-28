import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { DBFamilyUpdateComponent } from 'app/entities/db-family/db-family-update.component';
import { DBFamilyService } from 'app/entities/db-family/db-family.service';
import { DBFamily } from 'app/shared/model/db-family.model';

describe('Component Tests', () => {
  describe('DBFamily Management Update Component', () => {
    let comp: DBFamilyUpdateComponent;
    let fixture: ComponentFixture<DBFamilyUpdateComponent>;
    let service: DBFamilyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [DBFamilyUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DBFamilyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DBFamilyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DBFamilyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DBFamily(123);
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
        const entity = new DBFamily();
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
