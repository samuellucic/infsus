'use client';

import { createTheme, CssBaseline, ThemeProvider } from '@mui/material';
import Header from '../components/Header/Header';
import { headerItems } from '../utils/headerConstants';

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
  },
});

const AppLayout = ({ children }: Readonly<{ children: React.ReactNode }>) => {
  return (
    <>
      <ThemeProvider theme={darkTheme}>
        <CssBaseline />
        {children}
      </ThemeProvider>
    </>
  );
};

export default AppLayout;
