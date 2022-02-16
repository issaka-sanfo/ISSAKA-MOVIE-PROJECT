import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MovieUser from './movie-user';
import MovieUserDetail from './movie-user-detail';
import MovieUserUpdate from './movie-user-update';
import MovieUserDeleteDialog from './movie-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MovieUserDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MovieUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MovieUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MovieUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={MovieUser} />
    </Switch>
  </>
);

export default Routes;
