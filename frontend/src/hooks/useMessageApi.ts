import { ErrorResponse } from '../types/api/response.ts';
import message from 'antd/es/message';

export function useMessageApi() {
  const [messageApi, contextHolder] = message.useMessage();

  function showErrorToast(error: ErrorResponse) {
    const title = `[${error.code}] ${error.message}`;
    messageApi.open({ type: 'error', content: title });
  }

  function showSuccessToast(message: string) {
    messageApi.open({ type: 'success', content: message });
  }

  return { showErrorToast, showSuccessToast, contextHolder };
}
