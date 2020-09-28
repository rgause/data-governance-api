import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataGovernanceApiSharedModule } from 'app/shared/shared.module';
import { ConcernTypeComponent } from './concern-type.component';
import { ConcernTypeDetailComponent } from './concern-type-detail.component';
import { ConcernTypeUpdateComponent } from './concern-type-update.component';
import { ConcernTypeDeleteDialogComponent } from './concern-type-delete-dialog.component';
import { concernTypeRoute } from './concern-type.route';

@NgModule({
  imports: [DataGovernanceApiSharedModule, RouterModule.forChild(concernTypeRoute)],
  declarations: [ConcernTypeComponent, ConcernTypeDetailComponent, ConcernTypeUpdateComponent, ConcernTypeDeleteDialogComponent],
  entryComponents: [ConcernTypeDeleteDialogComponent],
})
export class DataGovernanceApiConcernTypeModule {}
