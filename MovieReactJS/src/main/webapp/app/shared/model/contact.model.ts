import { Moment } from 'moment';
import { IAddress } from 'app/shared/model/address.model';

export interface IContact {
  id?: number;
  name?: string;
  birthDate?: Moment;
  gender?: string;
  email?: string;
  movieUserId?: number;
  addresses?: IAddress[];
}

export const defaultValue: Readonly<IContact> = {};
