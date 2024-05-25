import { z } from 'zod';
import {
  descriptionConstants,
  nameConstants,
} from '../utils/realEstateTypeValidation';

const isNameValid = (name: string) => name.length >= nameConstants.minLength;

const isDescriptionValid = (description: string) =>
  description.length >= descriptionConstants.minLength;

export const EstateTypeSchema = z
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
export type EstateTypeFormType = z.infer<typeof EstateTypeSchema>;

export const EstateSchema = z.object({
  price: z.coerce.number(),
  address: z.string(),
  area: z.coerce.number(),
  description: z.string(),
  estateType: z.string(),
  town: z.number(),
});

export type EstateFormType = z.infer<typeof EstateSchema>;

export const OwnerSchema = z.object({
  name: z.string(),
  surname: z.string(),
  birthDate: z.string(),
  address: z.string(),
  email: z.string(),
  town: z.number(),
});

export type OwnerFormType = z.infer<typeof OwnerSchema>;
