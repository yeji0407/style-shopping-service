import React from 'react';
import CategoryPriceRangeTable from '../components/CategoryPriceRangeTable.tsx';

const LowestPricePage: React.FC = () => {
  return (
    <div style={{ maxWidth: 800, margin: '0 auto' }}>
      <CategoryPriceRangeTable />
    </div>
  );
};

export default LowestPricePage;
