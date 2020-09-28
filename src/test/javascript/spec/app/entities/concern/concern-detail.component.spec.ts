import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { ConcernDetailComponent } from 'app/entities/concern/concern-detail.component';
import { Concern } from 'app/shared/model/concern.model';

describe('Component Tests', () => {
  describe('Concern Management Detail Component', () => {
    let comp: ConcernDetailComponent;
    let fixture: ComponentFixture<ConcernDetailComponent>;
    const route = ({ data: of({ concern: new Concern(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [ConcernDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConcernDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConcernDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load concern on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.concern).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
