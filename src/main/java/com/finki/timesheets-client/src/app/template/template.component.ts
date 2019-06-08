import {Component, OnInit} from '@angular/core';
import {DownloadService} from "../services/download.service";
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-template',
  templateUrl: './template.component.html',
  styleUrls: ['./template.component.css']
})
export class TemplateComponent implements OnInit {

  classpathFileName: string;
  templatesForm: FormGroup;
  templateTypes = [
    {id: 1, type: 'invoice'},
    {id: 2, type: 'solution'},
    {id: 3, type: 'requirement'},
    {id: 4, type: 'coverLetter'}
  ];

  constructor(private downloadService: DownloadService, private fb: FormBuilder) {

    const formControls = this.templateTypes.map(type => new FormControl(false));
    const selectAllControl = new FormControl(false);

    this.templatesForm = this.fb.group({
      templateTypes: new FormArray(formControls),
      selectAll: selectAllControl
    })
  }

  ngOnInit() {
    this.onChanges();
  }

  downloadClasspathFile() {
    const selectedPreferences = this.templatesForm.value.templateTypes
      .map((checked, index) => checked ? this.templateTypes[index].type : null)
      .filter(value => value !== null);
    console.log(selectedPreferences);

    this.downloadService.downloadClasspathFile(selectedPreferences[0])
      .subscribe(response => {
        const filename = this.downloadService.getFileNameFromResponseContentDisposition(response);
        this.downloadService.saveFile(response.body, filename)
      })
  }

  private onChanges(): void {
    this.templatesForm.get('selectAll').valueChanges
      .subscribe(bool => {

        this.templatesForm.get('templateTypes')
          .patchValue(Array(this.templateTypes.length).fill(bool),
            {emitEvent: false});

      });

    this.templatesForm.get('templateTypes').valueChanges
      .subscribe(value => {
        const allSelected = value.every(bool => bool);

        if (this.templatesForm.get('selectAll').value !== allSelected) {
          this.templatesForm.get('selectAll')
            .patchValue(allSelected,
              {emitEvent: false})

        }

      })

  }
}
