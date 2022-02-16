import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './movie-user.reducer';
import { IMovieUser } from 'app/shared/model/movie-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMovieUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MovieUserDetail = (props: IMovieUserDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { movieUserEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          MovieUser [<b>{movieUserEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="username">Username</span>
          </dt>
          <dd>{movieUserEntity.username}</dd>
          <dt>Role</dt>
          <dd>
            {movieUserEntity.roles
              ? movieUserEntity.roles.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === movieUserEntity.roles.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>User</dt>
          <dd>{movieUserEntity.userLogin ? movieUserEntity.userLogin : ''}</dd>
        </dl>
        <Button tag={Link} to="/movie-user" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/movie-user/${movieUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ movieUser }: IRootState) => ({
  movieUserEntity: movieUser.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MovieUserDetail);
