import {
  ChangeEvent,
  ReactNode,
  useCallback,
  useEffect,
  useState,
} from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import { Column, Id, Pageable } from '../../../lib/types';
import { IconButton } from '@mui/material';
import { Delete } from '@mui/icons-material';

import styles from './GenericTable.module.css';

interface PaginationTableProps<T> {
  columns: Column<T>[];
  fetchData: (
    pageable: Pageable
  ) => Promise<{ content: (T & Id)[]; totalElements: number }>;
  onRowClick?: (id: any) => void;
  onItemDelete?: (id: any) => any;
}

const GenericTable = <T,>({
  columns,
  fetchData,
  onRowClick,
  onItemDelete,
}: PaginationTableProps<T>) => {
  const [rerender, setRerender] = useState<boolean>(false);

  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [rows, setRows] = useState<(T & Id)[]>([]);
  const [totalRows, setTotalRows] = useState(0);

  const fetchDataFromApi = useCallback(
    async (pageable: Pageable) => {
      try {
        const data = await fetchData(pageable);
        setRows(data.content);
        setTotalRows(data.totalElements);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },
    [fetchData]
  );

  useEffect(() => {
    fetchDataFromApi({ page, size });
  }, [fetchDataFromApi, page, size, rerender]);

  const handleChangePage = useCallback((event: unknown, newPage: number) => {
    setPage(newPage);
  }, []);

  const handleChangeRowsPerPage = useCallback(
    (event: ChangeEvent<HTMLInputElement>) => {
      setSize(+event.target.value);
      setPage(0);
    },
    []
  );

  const handleRowClick = (id: any) => {
    onRowClick?.(id);
  };

  const handleItemDelete = async (event: any, id: any) => {
    event.stopPropagation();
    await onItemDelete?.(id);
    setRerender((prevState) => !prevState);
  };

  return (
    <Paper sx={{ width: '100%', overflow: 'hidden' }}>
      <TableContainer sx={{ maxHeight: '80vh' }}>
        <Table stickyHeader aria-label="sticky table">
          <TableHead sx={{ backgroundColor: 'black' }}>
            <TableRow>
              {columns.map(({ id, label, align, minWidth }) => (
                <TableCell
                  key={id as string}
                  align={align}
                  sx={{
                    minWidth,
                  }}>
                  {label}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map((row, rowIndex) => (
              <TableRow
                className={styles.row}
                hover
                role="checkbox"
                tabIndex={-1}
                sx={{ cursor: 'pointer' }}
                key={rowIndex}
                onClick={() => handleRowClick(row.id)}>
                <>
                  {columns.map((column) => {
                    const value = row[column.id];
                    return (
                      <TableCell key={column.id as string} align={column.align}>
                        {
                          (column.format
                            ? column.format(value)
                            : value) as ReactNode
                        }
                      </TableCell>
                    );
                  })}
                  {onItemDelete && (
                    <TableCell>
                      <IconButton onClick={(e) => handleItemDelete(e, row.id)}>
                        <Delete />
                      </IconButton>
                    </TableCell>
                  )}
                </>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[10, 25, 100]}
        component="div"
        count={totalRows}
        rowsPerPage={size}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </Paper>
  );
};

export default GenericTable;
