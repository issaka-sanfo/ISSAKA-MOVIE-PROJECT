import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMovie } from 'app/shared/model/movie.model';
import { getEntities as getMovies } from 'app/entities/movie/movie.reducer';
import { IMovieUser } from 'app/shared/model/movie-user.model';
import { getEntities as getMovieUsers } from 'app/entities/movie-user/movie-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './seen-movie.reducer';
import { ISeenMovie } from 'app/shared/model/seen-movie.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISeenMovieUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SeenMovieUpdate = (props: ISeenMovieUpdateProps) => {
  const [movieId, setMovieId] = useState('0');
  const [movieUserId, setMovieUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { seenMovieEntity, movies, movieUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/seen-movie' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMovies();
    props.getMovieUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const entity = {
        ...seenMovieEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="movieJavaWebApp.seenMovie.home.createOrEditLabel">Create or edit a SeenMovie</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : seenMovieEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="seen-movie-id">ID</Label>
                  <AvInput id="seen-movie-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateLabel" for="seen-movie-date">
                  Date
                </Label>
                <AvInput
                  id="seen-movie-date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.seenMovieEntity.date)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="seen-movie-movie">Movie</Label>
                <AvInput id="seen-movie-movie" type="select" className="form-control" name="movieId">
                  <option value="" key="0" />
                  {movies
                    ? movies.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="seen-movie-movieUser">Movie User</Label>
                <AvInput id="seen-movie-movieUser" type="select" className="form-control" name="movieUserId">
                  <option value="" key="0" />
                  {movieUsers
                    ? movieUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/seen-movie" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  movies: storeState.movie.entities,
  movieUsers: storeState.movieUser.entities,
  seenMovieEntity: storeState.seenMovie.entity,
  loading: storeState.seenMovie.loading,
  updating: storeState.seenMovie.updating,
  updateSuccess: storeState.seenMovie.updateSuccess
});

const mapDispatchToProps = {
  getMovies,
  getMovieUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SeenMovieUpdate);
