export class QueryParams {
  pageNo: number;
  size: number;

  constructor(pageNo: number, size: number) {
    this.pageNo = pageNo;
    this.size = size;
  }
}

export class QueryParamsWithStatus {
  pageNo: number;
  size: number;
  status: string;

  constructor(pageNo: number, size: number, status: string) {
    this.pageNo = pageNo;
    this.size = size;
    this.status = status;
  }
}
