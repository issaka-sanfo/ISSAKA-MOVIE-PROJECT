import { Moment } from 'moment';
import { ISeenMovie } from 'app/shared/model/seen-movie.model';

export interface IMovie {
  id?: number;
  title?: string;
  date?: Moment;
  externalId?: string;
  seenMovies?: ISeenMovie[];
}

export const defaultValue: Readonly<IMovie> = {};
