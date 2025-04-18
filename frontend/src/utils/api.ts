import { ErrorResponse } from '../types/api/response.ts';

export const useApi = (showErrorToast: (error: ErrorResponse) => void) => {
  const call = async <T>(promise: Promise<{ data: CommonResponse<T> }>): Promise<T | null> => {
    try {
      const res = await promise;
      if (res.data.success) {
        return res.data.data;
      }

      showErrorToast(res.data.errors || '서버 요청 중 오류가 발생했습니다.');
    } catch (err: any) {
      showErrorToast(err.response?.data || '서버 요청 중 오류가 발생했습니다.');
      return null;
    }
    return null;
  };

  return { call };
};

type CommonResponse<T> = {
  success: boolean;
  data: T;
  code?: string;
  message?: string;
  errors?: any;
};
