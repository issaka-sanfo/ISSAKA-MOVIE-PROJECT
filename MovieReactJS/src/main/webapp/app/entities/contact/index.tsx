import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Contact from './contact';
import ContactDetail from './contact-detail';
import ContactUpdate from './contact-update';
import ContactDeleteDialog from './contact-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ContactDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ContactUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ContactUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ContactDetail} />
      <ErrorBoundaryRoute path={match.url} component={Contact} />
    </Switch>
  </>
);

export default Routes;
