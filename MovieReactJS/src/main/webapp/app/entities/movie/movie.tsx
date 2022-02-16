import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import {getEntities, getEntity} from './movie.reducer';
import { getLastEntity } from './movie.reducer';
import { IMovie } from 'app/shared/model/movie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import VideoPlayer from 'react-video-js-player';
import {EntitiesMenu} from "app/shared/layout/menus";

export interface IMovieProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {
  isAuthenticated: boolean;
  isAdmin: boolean;
}

export const Movie = (props: IMovieProps) => {
  const [paginationState, setPaginationState] = useState(getSortState(props.location, ITEMS_PER_PAGE));
  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  useEffect(() => {
    props.getLastEntity(0);
  }, []);

  const sortEntities = () => {
    getAllEntities();
    props.history.push(
      `${props.location.pathname}?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`
    );
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage
    });

  const { movieList, movieLastEntity, match, loading, totalItems } = props;
  return (
    <div>

      {movieLastEntity.id &&
      <h2 id="movie-heading">
        Last seen
      </h2>
      &&
      <Button tag={Link} to={`movie/${movieLastEntity.id}`} color="info" size="sm">
        <div>
          <VideoPlayer
            controls={true}
            src={movieLastEntity.externalId}
            poster='content/images/Poster.png'
            width="720"
            height="420"
          />
        </div>
      </Button>
      }
      <h2 id="movie-heading">
        Movies
      </h2>

      <div className="table-responsive">
        {movieList && movieList.length > 0 ? (

          <Table responsive>
            <thead>
            <tr>
              <th className="hand" onClick={sort('id')}>
                VIDEO<FontAwesomeIcon icon="sort" />
              </th>
              <th className="hand" onClick={sort('title')}>
                Title <FontAwesomeIcon icon="sort" />
              </th>
              <th className="hand" onClick={sort('date')}>
                Date <FontAwesomeIcon icon="sort" />
              </th>
              <th />
            </tr>
            </thead>
            <tbody>
            {movieList.map((movie, i) => (
              <tr key={`entity-${i}`}>
                <td>
                  <Button tag={Link} to={`movie/${movie.id}`} color="info" size="sm">
                    <div>
                      <VideoPlayer
                        controls={false}
                        src={false}
                        poster='content/images/Poster.png'
                        width="720"
                        height="420"
                      />
                    </div>
                  </Button>
                </td>
                <td>{movie.title}</td>
                <td>
                  <TextFormat type="date" value={movie.date} format={APP_DATE_FORMAT} />
                </td>

              </tr>
            ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">You are not connected, please login or register first!</div>
        )}
      </div>
      <div className={movieList && movieList.length > 0 ? '' : 'd-none'}>
        <Row className="justify-content-center">
          <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
        </Row>
        <Row className="justify-content-center">
          <JhiPagination
            activePage={paginationState.activePage}
            onSelect={handlePagination}
            maxButtons={5}
            itemsPerPage={paginationState.itemsPerPage}
            totalItems={props.totalItems}
          />
        </Row>
      </div>
    </div>
  );
};

const mapStateToProps = ({ movie }: IRootState) => ({
  movieList: movie.entities,
  loading: movie.loading,
  totalItems: movie.totalItems,
  movieLastEntity: movie.entity
});

const mapDispatchToProps = {
  getEntities, getLastEntity
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Movie);
