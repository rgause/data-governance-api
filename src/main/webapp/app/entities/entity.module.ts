import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'persona',
        loadChildren: () => import('./persona/persona.module').then(m => m.DataGovernanceApiPersonaModule),
      },
      {
        path: 'db-family',
        loadChildren: () => import('./db-family/db-family.module').then(m => m.DataGovernanceApiDBFamilyModule),
      },
      {
        path: 'db-source',
        loadChildren: () => import('./db-source/db-source.module').then(m => m.DataGovernanceApiDBSourceModule),
      },
      {
        path: 'db-database',
        loadChildren: () => import('./db-database/db-database.module').then(m => m.DataGovernanceApiDBDatabaseModule),
      },
      {
        path: 'db-table',
        loadChildren: () => import('./db-table/db-table.module').then(m => m.DataGovernanceApiDBTableModule),
      },
      {
        path: 'db-column',
        loadChildren: () => import('./db-column/db-column.module').then(m => m.DataGovernanceApiDBColumnModule),
      },
      {
        path: 'concern-type',
        loadChildren: () => import('./concern-type/concern-type.module').then(m => m.DataGovernanceApiConcernTypeModule),
      },
      {
        path: 'concern',
        loadChildren: () => import('./concern/concern.module').then(m => m.DataGovernanceApiConcernModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class DataGovernanceApiEntityModule {}
