import { getRealEstateTypesPaginated } from "@/app/api/RealEstateTypeApi";
import { Column, EstateType } from '@/app/lib/types';
import GenericTable from "../../CoreComponents/GenericTable";

const RealEstateTypeList = () => {
    const columns: Column<EstateType>[] = [
      { id: 'name', label: 'Name', minWidth: 170 },
      { id: 'description', label: 'Description', minWidth: 100 }
    ];
  
    return (
      <GenericTable<EstateType>
        columns={columns}
        fetchData={getRealEstateTypesPaginated}
      />
    );
  };
  
  export default RealEstateTypeList;