import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataGovernanceApiSharedModule } from 'app/shared/shared.module';
import { ConcernComponent } from './concern.component';
import { ConcernDetailComponent } from './concern-detail.component';
import { ConcernUpdateComponent } from './concern-update.component';
import { ConcernDeleteDialogComponent } from './concern-delete-dialog.component';
import { concernRoute } from './concern.route';

@NgModule({
  imports: [DataGovernanceApiSharedModule, RouterModule.forChild(concernRoute)],
  declarations: [ConcernComponent, ConcernDetailComponent, ConcernUpdateComponent, ConcernDeleteDialogComponent],
  entryComponents: [ConcernDeleteDialogComponent],
})
export class DataGovernanceApiConcernModule {}
