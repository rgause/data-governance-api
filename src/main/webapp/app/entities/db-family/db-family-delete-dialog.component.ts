import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDBFamily } from 'app/shared/model/db-family.model';
import { DBFamilyService } from './db-family.service';

@Component({
  templateUrl: './db-family-delete-dialog.component.html',
})
export class DBFamilyDeleteDialogComponent {
  dBFamily?: IDBFamily;

  constructor(protected dBFamilyService: DBFamilyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dBFamilyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dBFamilyListModification');
      this.activeModal.close();
    });
  }
}
