import { z } from 'zod';
import {
  descriptionConstants,
  nameConstants,
} from '../utils/estateTypeValidation';
import { estateConstants } from '@/app/utils/estateConstants';
import { ownerConstants } from '@/app/utils/ownerConstants';
import dayjs from 'dayjs';

const isNameValid = (name: string) =>
  name.length >= nameConstants.minLength &&
  name.length <= nameConstants.maxLength;

const isDescriptionValid = (description: string) =>
  description.length >= descriptionConstants.minLength &&
  description.length <= descriptionConstants.maxLength;

export const EstateTypeSchema = z
  .object({
    name: z.string({ required_error: 'Name is required' }),
    description: z.string({ required_error: 'Description is required' }),
  })
  .refine(({ name }) => isNameValid(name), {
    message: `Name of the real estate type should be longer than ${nameConstants.minLength} characters and shorter than ${nameConstants.maxLength}`,
    path: ['name'],
  })
  .refine(({ description }) => isDescriptionValid(description), {
    message: `Description of the real estate type should be longer than ${descriptionConstants.minLength} characters and shorter than ${nameConstants.maxLength}`,
    path: ['description'],
  });
export type EstateTypeFormType = z.infer<typeof EstateTypeSchema>;

export const EstateSchema = z
  .object({
    price: z.coerce.number({ required_error: 'Price is required' }),
    address: z.string({ required_error: 'Address is required' }),
    area: z.coerce.number({ required_error: 'Area is required' }),
    description: z.string({ required_error: 'Description is required' }),
    estateType: z.string({ required_error: 'Estate type is required' }),
    town: z.number({ required_error: 'Town is required' }),
  })
  .refine(({ price }) => price >= estateConstants.price.min, {
    message: `Price should be greater than ${estateConstants.price.min}`,
    path: ['price'],
  })
  .refine(
    ({ address }) =>
      address.length >= estateConstants.address.minLength &&
      address.length <= estateConstants.address.maxLength,
    {
      message: `Address of the estate should be longer than ${estateConstants.address.minLength} characters and shorter than ${estateConstants.address.maxLength}`,
      path: ['address'],
    }
  )
  .refine(({ area }) => area >= estateConstants.area.min, {
    message: `Area should be greater than ${estateConstants.area.min}`,
    path: ['area'],
  })
  .refine(
    ({ description }) =>
      description.length >= estateConstants.description.minLength &&
      description.length <= estateConstants.description.maxLength,
    {
      message: `Description of the estate should be longer than ${estateConstants.description.minLength} characters and shorter than ${estateConstants.description.maxLength}`,
      path: ['description'],
    }
  );

export type EstateFormType = z.infer<typeof EstateSchema>;

export const OwnerSchema = z
  .object({
    name: z.string({ required_error: 'Name is required' }),
    surname: z.string({ required_error: 'Surname is required' }),
    birthDate: z.custom((data) => data instanceof dayjs, {
      message: 'Invalid date',
      path: ['birthDate'],
    }),
    address: z.string({ required_error: 'Address is required' }),
    email: z.string({ required_error: 'Email is required' }).min(1).email(),
    town: z.number({ required_error: 'Town is required' }),
  })
  .refine(
    ({ name }) =>
      name.length >= ownerConstants.name.minLength &&
      name.length <= ownerConstants.name.maxLength,
    {
      message: `Owner's name should be longer than ${ownerConstants.name.minLength} characters and shorter than ${ownerConstants.name.maxLength}`,
      path: ['name'],
    }
  )
  .refine(
    ({ surname }) =>
      surname.length >= ownerConstants.surname.minLength &&
      surname.length <= ownerConstants.surname.maxLength,
    {
      message: `Owner's surname should be longer than ${ownerConstants.surname.minLength} characters and shorter than ${ownerConstants.surname.maxLength}`,
      path: ['surname'],
    }
  )
  .refine(
    ({ address }) =>
      address.length >= ownerConstants.address.minLength &&
      address.length <= ownerConstants.address.maxLength,
    {
      message: `Owner's address should be longer than ${ownerConstants.address.minLength} characters and shorter than ${ownerConstants.address.maxLength}`,
      path: ['address'],
    }
  )
  .refine(({ email }) => /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/.test(email), {
    message: 'Email needs to be valid',
    path: ['email'],
  });

export type OwnerFormType = z.infer<typeof OwnerSchema>;
