import { AtLeastOne } from './types';

type LenFunProps = AtLeastOne<{
  minLen: number;
  maxLen: number;
}>;

const regExp = {
  lengthAlpha: ({ minLen, maxLen }: LenFunProps) => {
    if (!minLen) {
      minLen = 0;
    }
    if (!maxLen) {
      maxLen = 255;
    }
    return new RegExp(`^[0-9a-zA-Z]{${minLen},${maxLen}}$`);
  },
};

export default regExp;
