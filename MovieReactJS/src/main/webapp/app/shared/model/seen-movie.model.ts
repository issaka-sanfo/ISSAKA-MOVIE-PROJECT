import { Moment } from 'moment';

export interface ISeenMovie {
  id?: number;
  date?: Moment;
  movieId?: number;
  movieUserId?: number;
}

export const defaultValue: Readonly<ISeenMovie> = {};
