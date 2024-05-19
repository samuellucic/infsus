export interface HeaderItem {
  title: string;
  label: string;
  url: string;
}

// "label" attr should be treated as UNIQUE
export const headerItems: HeaderItem[] = [
  {
    title: 'First title',
    label: 'real-estate-type-1',
    url: '/real-estate-type',
  },
  {
    title: 'Second title',
    label: 'real-estate-type-2',
    url: '/real-estate-type',
  },
];
