import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { DBColumnDetailComponent } from 'app/entities/db-column/db-column-detail.component';
import { DBColumn } from 'app/shared/model/db-column.model';

describe('Component Tests', () => {
  describe('DBColumn Management Detail Component', () => {
    let comp: DBColumnDetailComponent;
    let fixture: ComponentFixture<DBColumnDetailComponent>;
    const route = ({ data: of({ dBColumn: new DBColumn(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [DBColumnDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DBColumnDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DBColumnDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dBColumn on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dBColumn).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
