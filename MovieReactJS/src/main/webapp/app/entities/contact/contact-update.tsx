import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMovieUser } from 'app/shared/model/movie-user.model';
import { getEntities as getMovieUsers } from 'app/entities/movie-user/movie-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './contact.reducer';
import { IContact } from 'app/shared/model/contact.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IContactUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ContactUpdate = (props: IContactUpdateProps) => {
  const [movieUserId, setMovieUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { contactEntity, movieUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/contact' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMovieUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.birthDate = convertDateTimeToServer(values.birthDate);

    if (errors.length === 0) {
      const entity = {
        ...contactEntity,
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
          <h2 id="movieJavaWebApp.contact.home.createOrEditLabel">Create or edit a Contact</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : contactEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="contact-id">ID</Label>
                  <AvInput id="contact-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="contact-name">
                  Name
                </Label>
                <AvField id="contact-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="birthDateLabel" for="contact-birthDate">
                  Birth Date
                </Label>
                <AvInput
                  id="contact-birthDate"
                  type="datetime-local"
                  className="form-control"
                  name="birthDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.contactEntity.birthDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="genderLabel" for="contact-gender">
                  Gender
                </Label>
                <AvField id="contact-gender" type="text" name="gender" />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="contact-email">
                  Email
                </Label>
                <AvField id="contact-email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label for="contact-movieUser">Movie User</Label>
                <AvInput id="contact-movieUser" type="select" className="form-control" name="movieUserId">
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
              <Button tag={Link} id="cancel-save" to="/contact" replace color="info">
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
  movieUsers: storeState.movieUser.entities,
  contactEntity: storeState.contact.entity,
  loading: storeState.contact.loading,
  updating: storeState.contact.updating,
  updateSuccess: storeState.contact.updateSuccess
});

const mapDispatchToProps = {
  getMovieUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ContactUpdate);
