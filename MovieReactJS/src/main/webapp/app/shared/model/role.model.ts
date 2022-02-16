import { IMovieUser } from 'app/shared/model/movie-user.model';

export interface IRole {
  id?: number;
  name?: string;
  movieUsers?: IMovieUser[];
}

export const defaultValue: Readonly<IRole> = {};
