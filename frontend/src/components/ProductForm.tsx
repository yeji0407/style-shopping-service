import { Form, InputNumber, Modal, Select } from 'antd';
import { useEffect } from 'react';
import { Brand, Category, Product } from '../types/types.ts';

type Props = {
  open: boolean;
  onClose: () => void;
  onSubmit: (form: { brandId: number; categoryId: number; price: number }) => void;
  brands: Brand[];
  categories: Category[];
  initialValue?: Product | null;
};

const ProductForm = ({ open, onClose, onSubmit, brands, categories, initialValue }: Props) => {
  const [form] = Form.useForm();

  useEffect(() => {
    if (open) {
      if (initialValue) {
        form.setFieldsValue({ price: initialValue.price });
      } else {
        form.resetFields();
      }
    }
  }, [open, initialValue, form]);

  return (
    <Modal
      open={open}
      title={initialValue ? '상품 수정' : '상품 등록'}
      onCancel={() => {
        form.resetFields();
        onClose();
      }}
      onOk={() =>
        form
          .validateFields()
          .then(onSubmit)
          .then(() => form.resetFields())
      }
    >
      <Form form={form} layout="vertical">
        {!initialValue && (
          <>
            <Form.Item name="brandId" label="브랜드" rules={[{ required: true }]}>
              <Select options={brands.map((b) => ({ label: b.name, value: b.id }))} />
            </Form.Item>
            <Form.Item name="categoryId" label="카테고리" rules={[{ required: true }]}>
              <Select options={categories.map((c) => ({ label: c.name, value: c.id }))} />
            </Form.Item>
          </>
        )}
        <Form.Item name="price" label="가격" rules={[{ required: true }]}>
          <InputNumber min={100} step={100} style={{ width: '100%' }} />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default ProductForm;
