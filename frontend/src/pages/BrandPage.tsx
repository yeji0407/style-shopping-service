import { useEffect, useState } from 'react';
import { Table, Button, Space, Popconfirm } from 'antd';
import axios from 'axios';
import BrandForm from '../components/BrandForm';
import { Brand } from '../types/types.ts';
import { useApi } from '../utils/api.ts';
import { useMessageApi } from '../hooks/useMessageApi.ts';

const BrandPage = () => {
  const { showErrorToast, showSuccessToast, contextHolder } = useMessageApi();
  const [brands, setBrands] = useState<Brand[]>([]);
  const [loading, setLoading] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState<Brand | null>(null);
  const { call } = useApi(showErrorToast);

  const fetchBrands = async () => {
    setLoading(true);
    const data = await call<Brand[]>(axios.get('/api/brand/list'));
    if (data) setBrands(data);
    setLoading(false);
  };

  const handleSubmit = async (name: string) => {
    const isEdit = !!editing;
    const url = isEdit ? `/api/brand/update/${editing!.id}` : '/api/brand/create';
    const result = await call<Brand>(axios.post(url, { name }));
    if (result) {
      showSuccessToast(isEdit ? '수정 완료' : '등록 완료');
      setModalOpen(false);
      setEditing(null);
      fetchBrands();
    }
  };

  const handleDelete = async (id: number) => {
    const result = await call<{ id: number }>(axios.post(`/api/brand/delete/${id}`));
    if (result) {
      showSuccessToast('삭제 완료');
      fetchBrands();
    }
  };

  useEffect(() => {
    fetchBrands();
  }, []);

  return (
    <div>
      {contextHolder}
      <h2>브랜드 관리</h2>
      <Button type="primary" onClick={() => setModalOpen(true)} style={{ marginBottom: 16 }}>
        브랜드 등록
      </Button>
      <Table dataSource={brands} rowKey="id" loading={loading} pagination={false}>
        <Table.Column title="ID" dataIndex="id" />
        <Table.Column title="브랜드명" dataIndex="name" />
        <Table.Column
          title="관리"
          render={(_, brand: Brand) => (
            <Space>
              <Button
                onClick={() => {
                  setEditing(brand);
                  setModalOpen(true);
                }}
              >
                수정
              </Button>
              <Popconfirm title="정말 삭제하시겠습니까?" onConfirm={() => handleDelete(brand.id)}>
                <Button danger>삭제</Button>
              </Popconfirm>
            </Space>
          )}
        />
      </Table>

      <BrandForm
        open={modalOpen}
        onClose={() => {
          setModalOpen(false);
          setEditing(null);
        }}
        onSubmit={handleSubmit}
        initialValue={editing}
      />
    </div>
  );
};

export default BrandPage;
