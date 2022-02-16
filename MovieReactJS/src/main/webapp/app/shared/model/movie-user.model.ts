import { ISeenMovie } from 'app/shared/model/seen-movie.model';
import { IRole } from 'app/shared/model/role.model';

export interface IMovieUser {
  id?: number;
  username?: string;
  seenMovies?: ISeenMovie[];
  roles?: IRole[];
  contactId?: number;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IMovieUser> = {};
