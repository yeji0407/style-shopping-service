import { Layout, Menu } from 'antd';
import { Link, useLocation } from 'react-router-dom';
import type { MenuProps } from 'antd';

const { Header, Content } = Layout;

const MainLayout = ({ children }: { children: React.ReactNode }) => {
  const location = useLocation();

  const items: MenuProps['items'] = [
    {
      key: '/',
      label: <Link to="/">카테고리별 최저가</Link>,
    },
    {
      key: '/brand-lowest',
      label: <Link to="/brand-lowest">최저가 브랜드</Link>,
    },
    {
      key: '/category-price-range',
      label: <Link to="/category-price-range">카테고리 최저/최고가</Link>,
    },
    {
      key: '/brand',
      label: <Link to="/brand">브랜드 관리</Link>,
    },
    {
      key: '/product',
      label: <Link to="/product">상품 관리</Link>,
    },
  ];

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Header style={{ background: '#fff', padding: 0 }}>
        <Menu mode="horizontal" selectedKeys={[location.pathname]} items={items} />
      </Header>
      <Content style={{ padding: '24px' }}>{children}</Content>
    </Layout>
  );
};

export default MainLayout;
