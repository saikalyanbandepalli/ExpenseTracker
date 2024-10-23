import { createSlice } from '@reduxjs/toolkit';

export const expenseSlice = createSlice({
  name: 'expenses',
  initialState: {
    expenses: [],
  },
  reducers: {
    setExpenses: (state, action) => {
      state.expenses = action.payload;
    },
  },
});

export const { setExpenses } = expenseSlice.actions;
export default expenseSlice.reducer;
