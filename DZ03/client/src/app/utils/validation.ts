export type ValidationRequirement = {
  message: string;
  regex: RegExp;
};

export type ValidationRequirements = Record<string, ValidationRequirement>;

export const checkValueRequirements = <T extends ValidationRequirements>(
  validatingValue: string,
  requirements: T
) => {
  return Object.fromEntries(
    Object.entries(requirements).map(
      ([key, value]: [keyof T, ValidationRequirement]) => [
        key,
        value.regex.test(validatingValue),
      ]
    )
  ) as Record<keyof T, boolean>;
};
