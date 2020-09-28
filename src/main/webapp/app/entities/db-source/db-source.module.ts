import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataGovernanceApiSharedModule } from 'app/shared/shared.module';
import { DBSourceComponent } from './db-source.component';
import { DBSourceDetailComponent } from './db-source-detail.component';
import { DBSourceUpdateComponent } from './db-source-update.component';
import { DBSourceDeleteDialogComponent } from './db-source-delete-dialog.component';
import { dBSourceRoute } from './db-source.route';

@NgModule({
  imports: [DataGovernanceApiSharedModule, RouterModule.forChild(dBSourceRoute)],
  declarations: [DBSourceComponent, DBSourceDetailComponent, DBSourceUpdateComponent, DBSourceDeleteDialogComponent],
  entryComponents: [DBSourceDeleteDialogComponent],
})
export class DataGovernanceApiDBSourceModule {}
