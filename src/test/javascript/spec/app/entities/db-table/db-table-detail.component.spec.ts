import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { DBTableDetailComponent } from 'app/entities/db-table/db-table-detail.component';
import { DBTable } from 'app/shared/model/db-table.model';

describe('Component Tests', () => {
  describe('DBTable Management Detail Component', () => {
    let comp: DBTableDetailComponent;
    let fixture: ComponentFixture<DBTableDetailComponent>;
    const route = ({ data: of({ dBTable: new DBTable(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [DBTableDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DBTableDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DBTableDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dBTable on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dBTable).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
