import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SeenMovie from './seen-movie';
import SeenMovieDetail from './seen-movie-detail';
import SeenMovieUpdate from './seen-movie-update';
import SeenMovieDeleteDialog from './seen-movie-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SeenMovieDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SeenMovieUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SeenMovieUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SeenMovieDetail} />
      <ErrorBoundaryRoute path={match.url} component={SeenMovie} />
    </Switch>
  </>
);

export default Routes;
