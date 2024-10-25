export const initialState: OnActionState = {
  pendingFrRequest: false,
  acceptFrRequest: false,
};

export interface OnActionState {
  pendingFrRequest: boolean;
  acceptFrRequest: boolean;
}
