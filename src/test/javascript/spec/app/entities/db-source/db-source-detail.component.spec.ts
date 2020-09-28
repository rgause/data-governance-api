import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { DBSourceDetailComponent } from 'app/entities/db-source/db-source-detail.component';
import { DBSource } from 'app/shared/model/db-source.model';

describe('Component Tests', () => {
  describe('DBSource Management Detail Component', () => {
    let comp: DBSourceDetailComponent;
    let fixture: ComponentFixture<DBSourceDetailComponent>;
    const route = ({ data: of({ dBSource: new DBSource(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [DBSourceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DBSourceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DBSourceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dBSource on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dBSource).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
