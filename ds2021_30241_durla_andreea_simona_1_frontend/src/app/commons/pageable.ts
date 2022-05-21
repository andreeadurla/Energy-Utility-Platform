export interface Pageable {
    page: number;
    size: number;
}

export class PageableBuilder {
  private readonly _pageable: Pageable;

  constructor() {
    this._pageable = {
      page: 0,
      size: 15
    };
  }

  page(page: number): PageableBuilder {
    this._pageable.page = page;
    return this;
  }
  
  size(size: number): PageableBuilder {
    this._pageable.size = size;
    return this;
  }

  build(): Pageable {
    return this._pageable;
  }
}
