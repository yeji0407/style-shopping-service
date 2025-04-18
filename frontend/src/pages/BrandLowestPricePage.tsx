import React from 'react';
import BrandLowestPriceTable from '../components/BrandLowestPriceTable.tsx';

const LowestPricePage: React.FC = () => {
  return (
    <div style={{ maxWidth: 800, margin: '0 auto' }}>
      <BrandLowestPriceTable />
    </div>
  );
};

export default LowestPricePage;
