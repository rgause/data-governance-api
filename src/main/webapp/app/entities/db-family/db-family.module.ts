import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DataGovernanceApiSharedModule } from 'app/shared/shared.module';
import { DBFamilyComponent } from './db-family.component';
import { DBFamilyDetailComponent } from './db-family-detail.component';
import { DBFamilyUpdateComponent } from './db-family-update.component';
import { DBFamilyDeleteDialogComponent } from './db-family-delete-dialog.component';
import { dBFamilyRoute } from './db-family.route';

@NgModule({
  imports: [DataGovernanceApiSharedModule, RouterModule.forChild(dBFamilyRoute)],
  declarations: [DBFamilyComponent, DBFamilyDetailComponent, DBFamilyUpdateComponent, DBFamilyDeleteDialogComponent],
  entryComponents: [DBFamilyDeleteDialogComponent],
})
export class DataGovernanceApiDBFamilyModule {}
