export interface FieldError {
  field: string;
  value: any;
  reason: string;
}

export interface SuccessResponse<T> {
  success: true;
  data: T;
}

export interface ErrorResponse {
  success: false;
  code: string;
  message: string;
  errors?: FieldError[];
}

export type Response<T> = SuccessResponse<T> | ErrorResponse;