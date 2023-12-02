import { Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { debounceTime, distinctUntilChanged, fromEvent } from 'rxjs';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

  @Input() placeholder: string;
  public trDisabled: boolean = false;
  private defaultSearchText: string = "";
  searchText = new FormControl({ value: "", disabled: this.trDisabled });
  @ViewChild("searchPopRef") searchPopRef: ElementRef;
  @Output() searchTextEvent: EventEmitter<any> = new EventEmitter();
  constructor() { }


  @HostListener("document:keyup.enter", ["$event"])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.keyCode === 13) {
      this.searchTextEvent.emit(this.searchText);
    }
  }

  ngAfterViewInit() {
    setTimeout(() => {
      // this.searchPopRef.nativeElement.focus();
    }, 100);
    fromEvent(this.searchPopRef.nativeElement, "keyup")
      .pipe(debounceTime(500), distinctUntilChanged())
      .subscribe(($event: any) => {
        this.searchTextEvent.emit($event.target.value.trim());
      });
  }}
