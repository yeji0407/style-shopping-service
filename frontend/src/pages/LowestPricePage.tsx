import React from "react";
import LowestPriceTable from "../components/LowestPriceTable";

const LowestPricePage: React.FC = () => {
  return (
    <div style={{ maxWidth: 800, margin: "0 auto" }}>
      <LowestPriceTable />
    </div>
  );
};

export default LowestPricePage;