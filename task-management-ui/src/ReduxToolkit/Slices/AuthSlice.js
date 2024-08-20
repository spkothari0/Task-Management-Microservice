import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { baseUrl, setAuthHeader } from "../../Api/api";
import axios from "axios";

export const login = createAsyncThunk("auth/login", async (user, { rejectWithValue }) => {
    try {
        const {data} = await axios.post(`${baseUrl}/api/v1/auth/login`, user);
        localStorage.setItem("jwt", data.token);
        console.log("Login Success: ", data);
        return data; 
    } catch (error) {
        console.log("Error: ", error);
        // throw Error(error.response.data.message);
        return rejectWithValue(error.message);
    }
});

export const register = createAsyncThunk("auth/register", async (user, { rejectWithValue }) => {
    try {
        const {data} = await axios.post(`${baseUrl}/api/v1/user/register`, user);
        localStorage.setItem("jwt", data.token);
        console.log("Register Success: ", data);
        return data; 
    } catch (error) {
        console.log("Error: ", error);
        return rejectWithValue(error.message);
    }
});

export const logout = createAsyncThunk("auth/logout", async (user, { rejectWithValue }) => {
    try {
        localStorage.clear();
        console.log("Logout Success");
        return user;
    } catch (error) {
        console.log("Error: ", error);
        return rejectWithValue(error.message);
    }
});

export const getUser = createAsyncThunk("auth/getUser", async (user, { rejectWithValue }) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const {data} = await api.get(`/api/v1/user/`);
        console.log("Get User Success: ", data);
        return data; 
    } catch (error) {
        console.log("Error: ", error);
        return rejectWithValue(error.message);
    }
});

export const getUserList = createAsyncThunk("auth/getUserList", async (user, { rejectWithValue }) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const {data} = await api.get(`/api/v1/user/all`);
        console.log("Get User List Success: ", data);
        return data;
    } catch (error) {
        console.log("Error: ", error);
        return rejectWithValue(error.message);
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