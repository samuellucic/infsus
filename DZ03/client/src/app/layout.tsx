import type { Metadata } from 'next';
import { Inter } from 'next/font/google';
import './globals.css';
import Header from './components/Header/Header';
import { headerItems } from './utils/headerConstants';

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
  title: 'NIS',
  description: 'Real estate agency information system',
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <Header headerItems={headerItems} />
        {children}
      </body>
    </html>
  );
}
