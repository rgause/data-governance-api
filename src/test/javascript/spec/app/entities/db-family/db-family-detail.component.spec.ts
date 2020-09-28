import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { DBFamilyDetailComponent } from 'app/entities/db-family/db-family-detail.component';
import { DBFamily } from 'app/shared/model/db-family.model';

describe('Component Tests', () => {
  describe('DBFamily Management Detail Component', () => {
    let comp: DBFamilyDetailComponent;
    let fixture: ComponentFixture<DBFamilyDetailComponent>;
    const route = ({ data: of({ dBFamily: new DBFamily(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [DBFamilyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DBFamilyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DBFamilyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dBFamily on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dBFamily).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
