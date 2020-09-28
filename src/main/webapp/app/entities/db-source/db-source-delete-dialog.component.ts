import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDBSource } from 'app/shared/model/db-source.model';
import { DBSourceService } from './db-source.service';

@Component({
  templateUrl: './db-source-delete-dialog.component.html',
})
export class DBSourceDeleteDialogComponent {
  dBSource?: IDBSource;

  constructor(protected dBSourceService: DBSourceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dBSourceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dBSourceListModification');
      this.activeModal.close();
    });
  }
}
