import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataGovernanceApiSharedModule } from 'app/shared/shared.module';
import { DBTableComponent } from './db-table.component';
import { DBTableDetailComponent } from './db-table-detail.component';
import { DBTableUpdateComponent } from './db-table-update.component';
import { DBTableDeleteDialogComponent } from './db-table-delete-dialog.component';
import { dBTableRoute } from './db-table.route';

@NgModule({
  imports: [DataGovernanceApiSharedModule, RouterModule.forChild(dBTableRoute)],
  declarations: [DBTableComponent, DBTableDetailComponent, DBTableUpdateComponent, DBTableDeleteDialogComponent],
  entryComponents: [DBTableDeleteDialogComponent],
})
export class DataGovernanceApiDBTableModule {}
