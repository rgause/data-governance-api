import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { ConcernTypeDetailComponent } from 'app/entities/concern-type/concern-type-detail.component';
import { ConcernType } from 'app/shared/model/concern-type.model';

describe('Component Tests', () => {
  describe('ConcernType Management Detail Component', () => {
    let comp: ConcernTypeDetailComponent;
    let fixture: ComponentFixture<ConcernTypeDetailComponent>;
    const route = ({ data: of({ concernType: new ConcernType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [ConcernTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConcernTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConcernTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load concernType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.concernType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
