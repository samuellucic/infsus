import { z } from 'zod';
import {
  descriptionConstants,
  nameConstants,
} from '../utils/realEstateTypeValidation';

const isNameValid = (name: string) => name.length >= nameConstants.minLength;

const isDescriptionValid = (description: string) =>
  description.length >= descriptionConstants.minLength;

export const RealEstateTypeSchema = z
  .object({
    name: z.string(),
    description: z.string(),
  })
  .refine(({ name }) => isNameValid(name), {
    message: 'Name of the real estate type should be longer than 4 characters',
    path: ['name'],
  })
  .refine(({ description }) => isDescriptionValid(description), {
    message:
      'Description of the real estate type should be longer than 4 characters',
    path: ['description'],
  });
export type RealEstateTypeFormType = z.infer<typeof RealEstateTypeSchema>;
