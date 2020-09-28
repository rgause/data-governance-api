import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDBDatabase } from 'app/shared/model/db-database.model';
import { DBDatabaseService } from './db-database.service';

@Component({
  templateUrl: './db-database-delete-dialog.component.html',
})
export class DBDatabaseDeleteDialogComponent {
  dBDatabase?: IDBDatabase;

  constructor(
    protected dBDatabaseService: DBDatabaseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dBDatabaseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dBDatabaseListModification');
      this.activeModal.close();
    });
  }
}
