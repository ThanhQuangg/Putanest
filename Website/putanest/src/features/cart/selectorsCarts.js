import { createSelector } from 'reselect';

const selectCartState = state => state.carts;

export const selectCarts = createSelector(
  [selectCartState],
  cartsState => cartsState.items
);