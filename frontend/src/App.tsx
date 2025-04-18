import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LowestPricePage from './pages/LowestPricePage';
import MainLayout from './layout/MainLayout.tsx';
import BrandPage from './pages/BrandPage.tsx';
import ProductPage from './pages/ProductPage.tsx';
import CategoryPriceRangePage from './pages/CategoryPriceRangePage.tsx';
import BrandLowestPricePage from './pages/BrandLowestPricePage.tsx';

function App() {
  return (
    <Router>
      <MainLayout>
        <Routes>
          <Route path="/" element={<LowestPricePage />} />
          <Route path="/brand-lowest" element={<BrandLowestPricePage />} />
          <Route path="/category-price-range" element={<CategoryPriceRangePage />} />
          <Route path="/brand" element={<BrandPage />} />
          <Route path="/product" element={<ProductPage />} />
        </Routes>
      </MainLayout>
      <Routes>
        <Route path="/" element={<LowestPricePage />} />
      </Routes>
    </Router>
  );
}

export default App;
