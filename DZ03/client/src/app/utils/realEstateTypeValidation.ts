import regExp from '../lib/regExp';
import {
  checkValueRequirements,
  ValidationRequirement,
  ValidationRequirements,
} from './validation';

export type RealEstateTypeRequirement = ValidationRequirement;
export type RealEstateTypeRequirements = ValidationRequirements;

export const nameConstants = {
  minLength: 4,
};

export const descriptionConstants = {
  minLength: 4,
};

export const nameRequirements = {
  length: {
    message: `Minimal length of the name is ${nameConstants.minLength}`,
    regex: regExp.lengthAlpha({ minLen: nameConstants.minLength }),
  },
} satisfies Record<string, RealEstateTypeRequirement>;

export const descriptionRequirements = {
  length: {
    message: `Minimal length of the description is ${descriptionConstants.minLength}`,
    regex: regExp.lengthAlpha({ minLen: descriptionConstants.minLength }),
  },
} satisfies Record<string, RealEstateTypeRequirement>;

export type RealEstateTypeNameRequirementsType = keyof typeof nameRequirements;
export type RealEstateTypeDescriptionRequirementsType =
  keyof typeof descriptionRequirements;

export const checkNameRequirements = (
  value: string
): Record<keyof RealEstateTypeRequirements, boolean> => {
  return checkValueRequirements(value, nameRequirements);
};

export const checkDescriptionRequirements = (
  value: string
): Record<keyof RealEstateTypeRequirements, boolean> => {
  return checkValueRequirements(value, descriptionRequirements);
};
