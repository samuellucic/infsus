'use client';

import { createTheme, CssBaseline, ThemeProvider } from '@mui/material';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import { ReactNode } from 'react';

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
  },
});

const AppLayout = ({ children }: Readonly<{ children: ReactNode }>) => {
  return (
    <>
      <ThemeProvider theme={darkTheme}>
        <LocalizationProvider
          dateAdapter={AdapterDayjs}
          adapterLocale={dayjs.locale('hr')}>
          <CssBaseline />
          {children}
        </LocalizationProvider>
      </ThemeProvider>
    </>
  );
};

export default AppLayout;
