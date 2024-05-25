export interface HeaderItem {
  title: string;
  label: string;
  url: string;
}

// "label" attr should be treated as UNIQUE
export const headerItems: HeaderItem[] = [
  {
    title: 'Owners',
    label: 'owners',
    url: '/owners',
  },
  {
    title: 'Estate types',
    label: 'estate-types',
    url: '/estate-types',
  },
];
