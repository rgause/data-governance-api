import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { ConcernUpdateComponent } from 'app/entities/concern/concern-update.component';
import { ConcernService } from 'app/entities/concern/concern.service';
import { Concern } from 'app/shared/model/concern.model';

describe('Component Tests', () => {
  describe('Concern Management Update Component', () => {
    let comp: ConcernUpdateComponent;
    let fixture: ComponentFixture<ConcernUpdateComponent>;
    let service: ConcernService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [ConcernUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ConcernUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConcernUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConcernService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Concern(123);
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
        const entity = new Concern();
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
