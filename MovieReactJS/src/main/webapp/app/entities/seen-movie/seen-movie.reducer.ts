import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISeenMovie, defaultValue } from 'app/shared/model/seen-movie.model';

export const ACTION_TYPES = {
  FETCH_SEENMOVIE_LIST: 'seenMovie/FETCH_SEENMOVIE_LIST',
  FETCH_SEENMOVIE: 'seenMovie/FETCH_SEENMOVIE',
  CREATE_SEENMOVIE: 'seenMovie/CREATE_SEENMOVIE',
  UPDATE_SEENMOVIE: 'seenMovie/UPDATE_SEENMOVIE',
  DELETE_SEENMOVIE: 'seenMovie/DELETE_SEENMOVIE',
  RESET: 'seenMovie/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISeenMovie>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type SeenMovieState = Readonly<typeof initialState>;

// Reducer

export default (state: SeenMovieState = initialState, action): SeenMovieState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SEENMOVIE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SEENMOVIE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SEENMOVIE):
    case REQUEST(ACTION_TYPES.UPDATE_SEENMOVIE):
    case REQUEST(ACTION_TYPES.DELETE_SEENMOVIE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SEENMOVIE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SEENMOVIE):
    case FAILURE(ACTION_TYPES.CREATE_SEENMOVIE):
    case FAILURE(ACTION_TYPES.UPDATE_SEENMOVIE):
    case FAILURE(ACTION_TYPES.DELETE_SEENMOVIE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SEENMOVIE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_SEENMOVIE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SEENMOVIE):
    case SUCCESS(ACTION_TYPES.UPDATE_SEENMOVIE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SEENMOVIE):
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

const apiUrl = 'api/seen-movies';

// Actions

export const getEntities: ICrudGetAllAction<ISeenMovie> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SEENMOVIE_LIST,
    payload: axios.get<ISeenMovie>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ISeenMovie> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SEENMOVIE,
    payload: axios.get<ISeenMovie>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISeenMovie> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SEENMOVIE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISeenMovie> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SEENMOVIE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISeenMovie> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SEENMOVIE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
