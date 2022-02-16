export interface IAddress {
  id?: number;
  country?: string;
  area?: string;
  city?: string;
  street?: string;
  number?: string;
  contactId?: number;
}

export const defaultValue: Readonly<IAddress> = {};
