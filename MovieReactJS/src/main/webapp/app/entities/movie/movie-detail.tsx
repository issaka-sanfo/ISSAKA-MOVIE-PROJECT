import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './movie.reducer';
import { IMovie } from 'app/shared/model/movie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import VideoPlayer from 'react-video-js-player';
import {EntitiesMenu} from "app/shared/layout/menus";

export interface IMovieDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
  isAuthenticated: boolean;
  isAdmin: boolean;
}

export const MovieDetail = (props: IMovieDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { movieEntity } = props;
  return (

    <Row>
      <div>
        <VideoPlayer
          controls={true}
          src={movieEntity.externalId}
          poster='content/images/Poster.png'
          width="720"
          height="420"
        />
      </div>
      <Col md="8">
        <dl className="jh-entity-details">
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{movieEntity.title}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            <TextFormat value={movieEntity.date} type="date" format={APP_DATE_FORMAT} />
          </dd>
        </dl>
        <Button tag={Link} to="/movie" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        {props.isAuthenticated && props.isAdmin &&
        <Button tag={Link} to={`/movie/${movieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
        }
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ movie }: IRootState) => ({
  movieEntity: movie.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieDetail);
