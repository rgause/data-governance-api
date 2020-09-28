import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DataGovernanceApiTestModule } from '../../../test.module';
import { DBDatabaseDetailComponent } from 'app/entities/db-database/db-database-detail.component';
import { DBDatabase } from 'app/shared/model/db-database.model';

describe('Component Tests', () => {
  describe('DBDatabase Management Detail Component', () => {
    let comp: DBDatabaseDetailComponent;
    let fixture: ComponentFixture<DBDatabaseDetailComponent>;
    const route = ({ data: of({ dBDatabase: new DBDatabase(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DataGovernanceApiTestModule],
        declarations: [DBDatabaseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DBDatabaseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DBDatabaseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dBDatabase on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dBDatabase).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
