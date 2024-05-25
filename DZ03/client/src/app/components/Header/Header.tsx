'use client';

import styles from './Header.module.css';
import classnames from 'classnames';
import { HeaderItem } from '../../utils/headerConstants';
import { useState } from 'react';
import { usePathname, useRouter } from 'next/navigation';

export interface HeaderProps {
  headerItems: HeaderItem[];
}

const Header = ({ headerItems }: HeaderProps) => {
  const router = useRouter();
  const pathname = usePathname();

  const [activeTab, setActiveTab] = useState<number>(
    headerItems.findIndex(({ url }) => pathname === url)
  );

  const handleTabChange = (tabId: number) => {
    setActiveTab(tabId);
    router.push(headerItems[tabId].url);
  };

  return (
    <header className={styles.header}>
      <nav className={styles.nav}>
        {headerItems.map(({ title, label }, index) => (
          <button
            key={label}
            className={classnames(styles.button, styles[label], {
              [styles.active]: activeTab === index,
            })}
            onClick={() => handleTabChange(index)}>
            {title}
          </button>
        ))}
      </nav>
    </header>
  );
};

export default Header;
