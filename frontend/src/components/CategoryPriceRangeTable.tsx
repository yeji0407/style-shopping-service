import { Card, Form, Button, Descriptions, Select } from 'antd';
import { useEffect, useState } from 'react';
import { CategoryPriceRangeResponse } from '../types/api/product.ts';
import { useMessageApi } from '../hooks/useMessageApi.ts';
import { useApi } from '../utils/api.ts';
import axios from 'axios';
import { Category } from '../types/types.ts';

export default function CategoryPriceRangeTable() {
  const [form] = Form.useForm();
  const { showErrorToast, contextHolder } = useMessageApi();
  const { call } = useApi(showErrorToast);
  const [result, setResult] = useState<CategoryPriceRangeResponse | null>(null);
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    (async () => {
      const res = await call<Category[]>(axios.get('/api/category/list'));
      if (res) {
        setCategories(res);
      }
    })();
  }, []);

  const selectOptions = categories.map((c) => ({
    label: c.name,
    value: c.name,
  }));

  const handleSearch = async (values: { categoryName: string }) => {
    const res = await call<CategoryPriceRangeResponse>(axios.post('/api/product/category-price-range', values));

    if (res) {
      setResult(res);
    }
  };

  return (
    <>
      {contextHolder}
      <Card title="카테고리별 최저/최고가 브랜드 조회">
        <Form form={form} layout="inline" onFinish={handleSearch}>
          <Form.Item name="categoryName" rules={[{ required: true, message: '카테고리를 선택해주세요.' }]}>
            <Select placeholder="카테고리를 선택하세요" options={selectOptions} style={{ width: 200 }} />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">
              조회
            </Button>
          </Form.Item>
        </Form>

        {result && (
          <Descriptions title={`카테고리: ${result.categoryName}`} bordered style={{ marginTop: 24 }}>
            <Descriptions.Item label="최저가 브랜드">
              {result.lowest.brandName} (가격 : {result.lowest.price.toLocaleString()}원)
            </Descriptions.Item>
            <Descriptions.Item label="최고가 브랜드">
              {result.highest.brandName} (가격 : {result.highest.price.toLocaleString()}원)
            </Descriptions.Item>
          </Descriptions>
        )}
      </Card>
    </>
  );
}
