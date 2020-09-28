import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataGovernanceApiSharedModule } from 'app/shared/shared.module';
import { DBDatabaseComponent } from './db-database.component';
import { DBDatabaseDetailComponent } from './db-database-detail.component';
import { DBDatabaseUpdateComponent } from './db-database-update.component';
import { DBDatabaseDeleteDialogComponent } from './db-database-delete-dialog.component';
import { dBDatabaseRoute } from './db-database.route';

@NgModule({
  imports: [DataGovernanceApiSharedModule, RouterModule.forChild(dBDatabaseRoute)],
  declarations: [DBDatabaseComponent, DBDatabaseDetailComponent, DBDatabaseUpdateComponent, DBDatabaseDeleteDialogComponent],
  entryComponents: [DBDatabaseDeleteDialogComponent],
})
export class DataGovernanceApiDBDatabaseModule {}
