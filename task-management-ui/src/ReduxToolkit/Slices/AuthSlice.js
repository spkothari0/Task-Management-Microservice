import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { api, baseUrl, setAuthHeader } from "../../Api/api";
import {handleApiResponse} from "./Utility";


export const login = createAsyncThunk("auth/login", async (user) => {
    try {
        const {data} = await api.post(`${baseUrl}/api/v1/auth/login`, user);
        const result = handleApiResponse(data);
        localStorage.setItem("jwt", result.jwtToken);
        return result; 
    } catch (error) {
        throw Error(error.response.data.message);
    }
});

export const register = createAsyncThunk("auth/register", async (user) => {
    try {
        const {data} = await api.post(`${baseUrl}/api/v1/user/register`, user);
        const result = handleApiResponse(data);
        localStorage.setItem("jwt", result.jwtToken);
        return result; 
    } catch (error) {
        throw Error(error.response.data.message);
    }
});

export const logout = createAsyncThunk("auth/logout", async (user) => {
    try {
        localStorage.clear();
        return user;
    } catch (error) {
        throw Error(error.response.data.message);
    }
});

export const getUser = createAsyncThunk("auth/getUser", async (jwt) => {
    setAuthHeader(jwt, api);
    try {
        const {data} = await api.get(`/api/v1/user/`);
        const result = handleApiResponse(data);
        return result; 
    } catch (error) {
        throw Error(error.response.data.message);
    }
});

export const getUserList = createAsyncThunk("auth/getUserList", async () => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const {data} = await api.get(`/api/v1/user/all`);
        const result = handleApiResponse(data);
        return result;
    } catch (error) {
        throw Error(error.response.data.message);
    }
});

const authSlice = createSlice({
    name: "auth",
    initialState: {
        user: null,
        userList: [],
        loggedIn: false,
        error: null,
        jwt: null,
        loading: false,
    },
    reducers: {},
    extraReducers: (builder)=>{
        builder.addCase(login.pending, (state, action) => {
            state.loading = true;
            state.error = null;
        })
        .addCase(login.fulfilled, (state, action) => {
            state.loading = false;
            state.loggedIn = true;
            // state.user = action.payload;
            state.jwt = localStorage.getItem("jwt"); // action.payload.jwt;
            state.error = null;
        })
        .addCase(login.rejected, (state, action) => {
            state.loading = false;
            // state.error = action.error.message;
            state.error = action.payload;
        })

        // Register
        .addCase(register.pending, (state, action) => {
            state.loading = true;
            state.error = null;
        })
        .addCase(register.fulfilled, (state, action) => {
            state.loading = false;
            state.loggedIn = true;
            // state.user = action.payload;
            state.jwt = localStorage.getItem("jwt"); // action.payload.jwt;
            state.error = null;
        })
        .addCase(register.rejected, (state, action) => {
            state.loading = false;
            // state.error = action.error.message;
            state.error = action.payload;
        })

        // get user profile
        .addCase(getUser.pending, (state, action) => {
            state.loading = true;
            state.error = null;
        })
        .addCase(getUser.fulfilled, (state, action) => {
            state.loading = false;
            state.loggedIn = true;
            state.user = action.payload;
            state.error = null;
        })
        .addCase(getUser.rejected, (state, action) => {
            state.loading = false;
            // state.error = action.error.message;
            state.error = action.payload;
        })

        // get user list
        .addCase(getUserList.pending, (state, action) => {
            state.loading = true;
            state.error = null;
        })
        .addCase(getUserList.fulfilled, (state, action) => {
            state.loading = false;
            state.loggedIn = true;
            state.userList = action.payload;
            state.error = null;
        })
        .addCase(getUserList.rejected, (state, action) => {
            state.loading = false;
            // state.error = action.error.message;
            state.error = action.payload;
        })
        
        // logout
        .addCase(logout.fulfilled,(state) => {
            state.loggedIn = false;
            state.user = null;
            state.jwt = null;
            state.error = null;
            state.userList = [];
        })
    }
});

export default authSlice.reducer;