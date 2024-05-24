import { getRealEstateTypesPaginated } from "@/app/api/RealEstateTypeApi";
import { Column, EstateType } from '@/app/lib/types';
import GenericTable from "../../CoreComponents/GenericTable";
import { realEstateTypeColumns } from '@/app/utils/realEstateTypeTableConstants';

const RealEstateTypeList = () => {
    const columns: Column<EstateType>[] = [
      { id: realEstateTypeColumns.nameColumnKey as keyof EstateType, label: realEstateTypeColumns.nameColumnLabel, minWidth: 170 },
      { id: realEstateTypeColumns.decriptionColumnKey as keyof EstateType, label: realEstateTypeColumns.decriptionColumnLabel, minWidth: 100 }
    ];
  
    return (
      <GenericTable<EstateType>
        columns={columns}
        fetchData={getRealEstateTypesPaginated}
      />
    );
  };
  
  export default RealEstateTypeList;