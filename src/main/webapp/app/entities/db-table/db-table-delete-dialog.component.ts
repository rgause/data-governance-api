import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDBTable } from 'app/shared/model/db-table.model';
import { DBTableService } from './db-table.service';

@Component({
  templateUrl: './db-table-delete-dialog.component.html',
})
export class DBTableDeleteDialogComponent {
  dBTable?: IDBTable;

  constructor(protected dBTableService: DBTableService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dBTableService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dBTableListModification');
      this.activeModal.close();
    });
  }
}
