import { Form, Input, Modal } from 'antd';
import { Brand } from '../types/types.ts';
import { useEffect } from 'react';

type Props = {
  open: boolean;
  onClose: () => void;
  onSubmit: (name: string) => void;
  initialValue?: Brand | null;
};

const BrandForm = ({ open, onClose, onSubmit, initialValue }: Props) => {
  const [form] = Form.useForm();

  useEffect(() => {
    if (initialValue) {
      form.setFieldsValue({ name: initialValue.name });
    } else {
      form.resetFields();
      form.setFieldsValue({ name: '' });
    }
  }, [initialValue, form]);

  return (
    <Modal
      open={open}
      title={initialValue ? '브랜드 수정' : '브랜드 등록'}
      onCancel={onClose}
      onOk={() =>
        form
          .validateFields()
          .then((values) => {
            onSubmit(values.name);
            form.resetFields();
          })
          .catch(() => {})
      }
    >
      <Form form={form} layout="vertical" initialValues={{ name: initialValue?.name }}>
        <Form.Item name="name" label="브랜드명" rules={[{ required: true, message: '브랜드명을 입력해주세요' }]}>
          <Input />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default BrandForm;
