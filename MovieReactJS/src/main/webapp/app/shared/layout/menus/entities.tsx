import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/movie">
      Movie
    </MenuItem>
    <MenuItem icon="asterisk" to="/seen-movie">
      Seen Movie
    </MenuItem>
    <MenuItem icon="asterisk" to="/contact">
      Contact
    </MenuItem>
    <MenuItem icon="asterisk" to="/role">
      Role
    </MenuItem>
    <MenuItem icon="asterisk" to="/address">
      Address
    </MenuItem>
  </NavDropdown>
);
