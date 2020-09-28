import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConcernType } from 'app/shared/model/concern-type.model';
import { ConcernTypeService } from './concern-type.service';

@Component({
  templateUrl: './concern-type-delete-dialog.component.html',
})
export class ConcernTypeDeleteDialogComponent {
  concernType?: IConcernType;

  constructor(
    protected concernTypeService: ConcernTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.concernTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('concernTypeListModification');
      this.activeModal.close();
    });
  }
}
