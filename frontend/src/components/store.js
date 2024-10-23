import { configureStore } from '@reduxjs/toolkit';
import expenseReducer from './expenseSlice';

// Load persisted state from local storage
const loadState = () => {
  try {
    const serializedState = localStorage.getItem('reduxState');
    if (serializedState === null) {
      return undefined; // No state in localStorage, return undefined for initial state
    }
    return JSON.parse(serializedState);
  } catch (err) {
    return undefined;
  }
};

// Save state to local storage
const saveState = (state) => {
  try {
    const serializedState = JSON.stringify(state);
    localStorage.setItem('reduxState', serializedState);
  } catch (err) {
    console.error('Could not save state', err);
  }
};

// Create store with persisted state
const persistedState = loadState();

export const store = configureStore({
  reducer: {
    expenses: expenseReducer,
  },
  preloadedState: persistedState, // Initialize store with persisted state
});

// Subscribe to store changes to save state in localStorage
store.subscribe(() => {
  saveState(store.getState());
});
