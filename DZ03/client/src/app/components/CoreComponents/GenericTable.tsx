import { useCallback, useEffect, useState } from 'react';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import { Column } from '@/app/lib/types';

interface PaginationTableProps<T> {
    columns: Column<T>[];
    fetchData: (page: number, size: number) => Promise<{ content: T[]; totalElements: number }>;
}

const GenericTable = <T,>({ columns, fetchData }: PaginationTableProps<T>) => {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [rows, setRows] = useState<T[]>([]);
    const [totalRows, setTotalRows] = useState(0);

    const fetchDataFromApi = useCallback(async (page: number, size: number) => {
        try {
            const data = await fetchData(page, size);
            setRows(data.content);
            setTotalRows(data.totalElements);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    }, [fetchData]);

    useEffect(() => {
        fetchDataFromApi(page, rowsPerPage);
    }, [fetchDataFromApi, page, rowsPerPage]);

    const handleChangePage = useCallback((event: unknown, newPage: number) => {
        setPage(newPage);
    }, []);

    const handleChangeRowsPerPage = useCallback((event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    }, []);

    return (
      <Paper sx={{ width: '100%', overflow: 'hidden' }}>
          <TableContainer sx={{ maxHeight: '80vh' }}>
              <Table stickyHeader aria-label="sticky table">
                  <TableHead>
                      <TableRow>
                          {columns.map((column) => (
                            <TableCell
                              key={column.id as string}
                              align={column.align}
                              style={{ minWidth: column.minWidth }}
                            >
                                {column.label}
                            </TableCell>
                          ))}
                      </TableRow>
                  </TableHead>
                  <TableBody>
                      {rows.map((row, rowIndex) => (
                        <TableRow hover role="checkbox" tabIndex={-1} key={rowIndex}>
                            {columns.map((column) => {
                                const value = row[column.id];
                                return (
                                  <TableCell key={column.id as string} align={column.align}>
                                      {(column.format ? column.format(value) : value) as React.ReactNode}
                                  </TableCell>
                                );
                            })}
                        </TableRow>
                      ))}
                  </TableBody>
              </Table>
          </TableContainer>
          <TablePagination
            rowsPerPageOptions={[10, 25, 100]}
            component="div"
            count={totalRows}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
      </Paper>
    );
};

export default GenericTable;
