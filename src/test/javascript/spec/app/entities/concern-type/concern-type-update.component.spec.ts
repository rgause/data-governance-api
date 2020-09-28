import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { ConcernTypeUpdateComponent } from 'app/entities/concern-type/concern-type-update.component';
import { ConcernTypeService } from 'app/entities/concern-type/concern-type.service';
import { ConcernType } from 'app/shared/model/concern-type.model';

describe('Component Tests', () => {
  describe('ConcernType Management Update Component', () => {
    let comp: ConcernTypeUpdateComponent;
    let fixture: ComponentFixture<ConcernTypeUpdateComponent>;
    let service: ConcernTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [ConcernTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConcernTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConcernTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConcernTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ConcernType(123);
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
        const entity = new ConcernType();
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
