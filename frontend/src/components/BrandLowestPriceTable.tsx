import { Table, Typography } from 'antd';
import { useEffect, useState } from 'react';
import { useMessageApi } from '../hooks/useMessageApi.ts';
import { useApi } from '../utils/api.ts';
import axios from 'axios';
import { BrandLowestPriceResponse } from '../types/api/product.ts';
const { Title } = Typography;

const columns = [
  {
    title: '카테고리',
    dataIndex: 'categoryName',
    key: 'categoryName',
  },
  {
    title: '가격',
    dataIndex: 'price',
    key: 'price',
    render: (price: number) => price.toLocaleString() + '원',
  },
];

export default function BrandLowestPriceTable() {
  const { showErrorToast, contextHolder } = useMessageApi();
  const { call } = useApi(showErrorToast);
  const [data, setData] = useState<BrandLowestPriceResponse | null>(null);

  useEffect(() => {
    (async () => {
      const result = await call<BrandLowestPriceResponse>(axios.get('/api/product/brand-lowest-prices'));
      if (result) {
        setData(result);
      }
    })().catch(showErrorToast);
  }, []);

  return (
    <>
      {contextHolder}
      <div style={{ maxWidth: 800, margin: '0 auto' }}>
        <Title level={3}>최저가 브랜드 구매 정보</Title>
        {data && (
          <>
            <Title level={5}>브랜드: {data.brandName}</Title>
            <Table columns={columns} dataSource={data.categories} rowKey="categoryName" pagination={false} />
            <div style={{ marginTop: 16, textAlign: 'right', fontWeight: 600 }}>
              총합: {data.totalPrice.toLocaleString()}원
            </div>
          </>
        )}
      </div>
    </>
  );
}
