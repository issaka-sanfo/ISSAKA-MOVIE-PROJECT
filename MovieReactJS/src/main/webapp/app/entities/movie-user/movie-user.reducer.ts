import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMovieUser, defaultValue } from 'app/shared/model/movie-user.model';

export const ACTION_TYPES = {
  FETCH_MOVIEUSER_LIST: 'movieUser/FETCH_MOVIEUSER_LIST',
  FETCH_MOVIEUSER: 'movieUser/FETCH_MOVIEUSER',
  CREATE_MOVIEUSER: 'movieUser/CREATE_MOVIEUSER',
  UPDATE_MOVIEUSER: 'movieUser/UPDATE_MOVIEUSER',
  DELETE_MOVIEUSER: 'movieUser/DELETE_MOVIEUSER',
  RESET: 'movieUser/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMovieUser>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MovieUserState = Readonly<typeof initialState>;

// Reducer

export default (state: MovieUserState = initialState, action): MovieUserState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MOVIEUSER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MOVIEUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MOVIEUSER):
    case REQUEST(ACTION_TYPES.UPDATE_MOVIEUSER):
    case REQUEST(ACTION_TYPES.DELETE_MOVIEUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MOVIEUSER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MOVIEUSER):
    case FAILURE(ACTION_TYPES.CREATE_MOVIEUSER):
    case FAILURE(ACTION_TYPES.UPDATE_MOVIEUSER):
    case FAILURE(ACTION_TYPES.DELETE_MOVIEUSER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEUSER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_MOVIEUSER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MOVIEUSER):
    case SUCCESS(ACTION_TYPES.UPDATE_MOVIEUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MOVIEUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/movie-users';

// Actions

export const getEntities: ICrudGetAllAction<IMovieUser> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MOVIEUSER_LIST,
    payload: axios.get<IMovieUser>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMovieUser> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MOVIEUSER,
    payload: axios.get<IMovieUser>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMovieUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MOVIEUSER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMovieUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MOVIEUSER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMovieUser> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MOVIEUSER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
