import { Button, message, Popconfirm, Space, Table } from 'antd';
import { useEffect, useState } from 'react';
import axios from 'axios';
import ProductForm from '../components/ProductForm';
import { Product, Brand, Category } from '../types/types.ts';
import { useApi } from '../utils/api';
import { useMessageApi } from '../hooks/useMessageApi.ts';

const ProductPage = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [brands, setBrands] = useState<Brand[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [editing, setEditing] = useState<Product | null>(null);
  const { showErrorToast, contextHolder } = useMessageApi();
  const { call } = useApi(showErrorToast);

  const fetchData = async () => {
    setLoading(true);
    const [p, b, c] = await Promise.all([
      call<Product[]>(axios.get('/api/product/list')),
      call<Brand[]>(axios.get('/api/brand/list')),
      call<Category[]>(axios.get('/api/category/list')),
    ]);
    if (p) setProducts(p);
    if (b) setBrands(b);
    if (c) setCategories(c);
    setLoading(false);
  };

  const handleSubmit = async (form: any) => {
    const url = editing ? `/api/product/update/${editing.id}` : '/api/product/create';
    const result = await call<Product>(axios.post(url, editing ? { price: form.price } : form));
    if (result) {
      message.success(editing ? '수정 완료' : '등록 완료');
      setModalOpen(false);
      setEditing(null);
      fetchData();
    }
  };

  const handleDelete = async (id: number) => {
    const result = await call<{ id: number }>(axios.post(`/api/product/delete/${id}`));
    if (result) {
      message.success('삭제 완료');
      fetchData();
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div>
      {contextHolder}
      <h2>상품 관리</h2>
      <Button type="primary" onClick={() => setModalOpen(true)} style={{ marginBottom: 16 }}>
        상품 등록
      </Button>
      <Table dataSource={products} rowKey="id" loading={loading} pagination={false}>
        <Table.Column title="ID" dataIndex="id" />
        <Table.Column title="브랜드" dataIndex="brandName" />
        <Table.Column title="카테고리" dataIndex="categoryName" />
        <Table.Column title="가격" dataIndex="price" />
        <Table.Column
          title="관리"
          render={(_, product: Product) => (
            <Space>
              <Button
                onClick={() => {
                  setEditing(product);
                  setModalOpen(true);
                }}
              >
                수정
              </Button>
              <Popconfirm title="정말 삭제하시겠습니까?" onConfirm={() => handleDelete(product.id)}>
                <Button danger>삭제</Button>
              </Popconfirm>
            </Space>
          )}
        />
      </Table>

      <ProductForm
        open={modalOpen}
        onClose={() => {
          setModalOpen(false);
          setEditing(null);
        }}
        onSubmit={handleSubmit}
        brands={brands}
        categories={categories}
        initialValue={editing}
      />
    </div>
  );
};

export default ProductPage;
