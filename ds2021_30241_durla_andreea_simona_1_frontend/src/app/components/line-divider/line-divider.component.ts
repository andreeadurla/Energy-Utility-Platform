import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-line-divider',
  templateUrl: './line-divider.component.html',
  styleUrls: ['./line-divider.component.scss']
})
export class LineDividerComponent implements OnInit {

  @Input() message!: string;

  constructor() { }

  ngOnInit(): void {
  }

}
