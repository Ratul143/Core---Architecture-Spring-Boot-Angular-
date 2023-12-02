export interface ApiResponse<T> {
  code: number;
  status: boolean;
  message: string;
  totalCount: number;
  data: T[];
}
