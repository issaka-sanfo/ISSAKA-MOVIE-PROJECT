import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Movie from './movie';
import MovieDetail from './movie-detail';
import MovieUpdate from './movie-update';
import MovieDeleteDialog from './movie-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MovieDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MovieUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MovieUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MovieDetail} />
      <ErrorBoundaryRoute path={match.url} component={Movie} />
    </Switch>
  </>
);

export default Routes;
