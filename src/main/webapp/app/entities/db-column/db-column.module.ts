import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataGovernanceApiSharedModule } from 'app/shared/shared.module';
import { DBColumnComponent } from './db-column.component';
import { DBColumnDetailComponent } from './db-column-detail.component';
import { DBColumnUpdateComponent } from './db-column-update.component';
import { DBColumnDeleteDialogComponent } from './db-column-delete-dialog.component';
import { dBColumnRoute } from './db-column.route';

@NgModule({
  imports: [DataGovernanceApiSharedModule, RouterModule.forChild(dBColumnRoute)],
  declarations: [DBColumnComponent, DBColumnDetailComponent, DBColumnUpdateComponent, DBColumnDeleteDialogComponent],
  entryComponents: [DBColumnDeleteDialogComponent],
})
export class DataGovernanceApiDBColumnModule {}
