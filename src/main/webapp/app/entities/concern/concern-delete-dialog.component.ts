import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConcern } from 'app/shared/model/concern.model';
import { ConcernService } from './concern.service';

@Component({
  templateUrl: './concern-delete-dialog.component.html',
})
export class ConcernDeleteDialogComponent {
  concern?: IConcern;

  constructor(protected concernService: ConcernService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.concernService.delete(id).subscribe(() => {
      this.eventManager.broadcast('concernListModification');
      this.activeModal.close();
    });
  }
}
