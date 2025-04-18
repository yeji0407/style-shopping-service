export interface LowestPriceItem {
  categoryId: number;
  categoryName: string;
  brandId: number;
  brandName: string;
  price: number;
}

export interface LowestPriceResponse {
  products: LowestPriceItem[];
  totalPrice: number;
}

export type BrandLowestPriceCategory = {
  categoryName: string;
  price: number;
};

export type BrandLowestPriceResponse = {
  brandName: string;
  categories: BrandLowestPriceCategory[];
  totalPrice: number;
};

export type CategoryPriceRangeProduct = {
  brandName: string;
  price: number;
};

export type CategoryPriceRangeResponse = {
  categoryName: string;
  lowest: CategoryPriceRangeProduct;
  highest: CategoryPriceRangeProduct;
};
