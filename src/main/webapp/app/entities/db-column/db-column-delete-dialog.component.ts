import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDBColumn } from 'app/shared/model/db-column.model';
import { DBColumnService } from './db-column.service';

@Component({
  templateUrl: './db-column-delete-dialog.component.html',
})
export class DBColumnDeleteDialogComponent {
  dBColumn?: IDBColumn;

  constructor(protected dBColumnService: DBColumnService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dBColumnService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dBColumnListModification');
      this.activeModal.close();
    });
  }
}
