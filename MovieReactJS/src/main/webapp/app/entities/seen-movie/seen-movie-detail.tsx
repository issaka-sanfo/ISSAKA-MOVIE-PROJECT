import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './seen-movie.reducer';
import { ISeenMovie } from 'app/shared/model/seen-movie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISeenMovieDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SeenMovieDetail = (props: ISeenMovieDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { seenMovieEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          SeenMovie [<b>{seenMovieEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            <TextFormat value={seenMovieEntity.date} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>Movie</dt>
          <dd>{seenMovieEntity.movieId ? seenMovieEntity.movieId : ''}</dd>
          <dt>Movie User</dt>
          <dd>{seenMovieEntity.movieUserId ? seenMovieEntity.movieUserId : ''}</dd>
        </dl>
        <Button tag={Link} to="/seen-movie" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/seen-movie/${seenMovieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ seenMovie }: IRootState) => ({
  seenMovieEntity: seenMovie.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SeenMovieDetail);
