import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';

@Component({
  selector: 'app-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.css'],
})
export class ImageUploadComponent implements OnInit, OnChanges {
  @ViewChild('inputFile') myInputVariable: ElementRef;
  file: string;
  images: any[] = [];

  selectedImages: any[] = [];
  @Output() getData = new EventEmitter<any[]>();
  @Input() resetComponent: boolean = false;

  constructor() {}

  ngOnInit(): void {}

  ngOnChanges() {
    if (this.resetComponent) {
      this.resetComponent = false;
      this.reset();
    }
  }

  onSelectFile(event: any) {
    if (event.target.files.length > 0) {
      var filesAmount = event.target.files.length;
      const formData = new FormData();

      for (let i = 0; i < filesAmount; i++) {
        formData.append('file', event.target.files[i]);
      }

      // this.issueFileUploadService.uploadImage(formData).subscribe(result => {
      //    if(result.code == '200'){
      //      this.selectedImages.push(...result.data);
      //     for (let i = 0; i < filesAmount; i++) {
      //       this.file = event.target.files[i];
      //       var reader = new FileReader();
      //       reader.onload = (data: any) => {
      //       this.images.push(data.target.result);
      //       }
      //     reader.readAsDataURL(event.target.files[i]);
      //      }
      //      this.myInputVariable.nativeElement.value = '';
      //      this.getData.emit(this.selectedImages);
      //    }
      // },
      // (error) => {
      //   }
      // );
    }
  }

  reset() {
    this.selectedImages = [];
    this.images = [];
    this.getData.emit([]);
  }

  removeImage(index: any) {
    this.selectedImages.splice(index, 1);
    this.images.splice(index, 1);
    this.getData.emit(this.selectedImages);
  }
}
