import React, { useEffect, useState } from 'react';
import { Table, Typography } from 'antd';
import { LowestPriceItem, LowestPriceResponse } from '../types/api/product.ts';
import axios from 'axios';
import { useApi } from '../utils/api.ts';
import { useMessageApi } from '../hooks/useMessageApi.ts';

const { Title } = Typography;

const LowestPriceTable: React.FC = () => {
  const [items, setItems] = useState<LowestPriceItem[]>([]);
  const [total, setTotal] = useState<number>(0);
  const { showErrorToast, contextHolder } = useMessageApi();
  const { call } = useApi(showErrorToast);

  useEffect(() => {
    (async () => {
      const res = await call<LowestPriceResponse>(axios.get('/api/product/lowest-prices'));

      if (res) {
        setItems(res.products);
        setTotal(res.totalPrice);
      }
    })();
  }, []);

  const columns = [
    {
      title: '카테고리',
      dataIndex: 'categoryName',
      key: 'categoryName',
    },
    {
      title: '브랜드',
      dataIndex: 'brandName',
      key: 'brandName',
    },
    {
      title: '가격',
      dataIndex: 'price',
      key: 'price',
      render: (price: number) => `${price.toLocaleString()}원`,
    },
  ];

  return (
    <>
      {contextHolder}
      <div style={{ maxWidth: 800, margin: '0 auto' }}>
        <Title level={3}>카테고리별 최저가 브랜드</Title>
        <Table
          dataSource={items}
          columns={columns}
          rowKey={(record) => `${record.categoryId}-${record.brandId}`}
          pagination={false}
        />
        <div style={{ marginTop: 16, textAlign: 'right', fontWeight: 600 }}>총합: {total.toLocaleString()}원</div>
      </div>
    </>
  );
};

export default LowestPriceTable;
