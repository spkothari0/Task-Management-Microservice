import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { thunk } from "redux-thunk";
import authReducer from "./Slices/AuthSlice";
import taskReducer from "./Slices/TaskSlice";
import submissionReducer from "./Slices/SubmissionSlice";

const rootReducer = combineReducers({
    auth:authReducer,
    task:taskReducer,
    submission:submissionReducer,
});

const store = configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(thunk),
});

export default store;