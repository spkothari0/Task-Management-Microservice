import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { api, setAuthHeader } from "../../Api/api";
import { handleApiResponse } from "./Utility";

export const submitTask = createAsyncThunk("submissions/submitTask", async ({taskId, repoLink}) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const { data } = await api.post(`/api/v1/submissions?taskId=${taskId}&repoLink=${repoLink}`,{});
        const result = handleApiResponse(data);
        return result;
    } catch (error) {
        throw Error(error.response.data.message);
    }
});

export const fetchAllSubmissions = createAsyncThunk("submissions/fetchAllSubmissions", async () => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const { data } = await api.get(`/api/v1/submissions`);
        const result = handleApiResponse(data);
        return result;
    } catch (error) {
        
        throw Error(error.response.data.message);
    }
});

export const fetchSubmissionsByTaskId = createAsyncThunk("submissions/fetchSubmissionsByTaskId", async (taskId) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const { data } = await api.get(`/api/v1/submissions/task/${taskId}`);
        const result = handleApiResponse(data);
        return result;
    } catch (error) {
        
        throw Error(error.response.data.message);
    }
});

export const acceptOrDeclineSubmission = createAsyncThunk("submissions/acceptOrDeclineSubmission", async ({submissionId, status}) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const { data } = await api.put(`/api/v1/submissions/${submissionId}/accept?accept=${status}`, {});
        const result = handleApiResponse(data);
        return result;
    } catch (error) {
        
        throw Error(error.response.data.message);
    }
});


const submissionSlice = createSlice({
    name: "submissions",
    initialState: {
        submissions: [],
        loading: false,
        error: null,
    },
    reducers: {},
    extraReducers:(builder)=>{
        builder
        .addCase(submitTask.pending, (state) => {
            state.loading = true;
        })
        .addCase(submitTask.fulfilled, (state, action) => {
            state.loading = false;
            state.submissions.push(action.payload);
        })
        .addCase(submitTask.rejected, (state, action) => {
            state.loading = false;
            state.error = action.payload;
        })
        .addCase(fetchAllSubmissions.pending, (state) => {
            state.loading = true;
        })
        .addCase(fetchAllSubmissions.fulfilled, (state, action) => {
            state.loading = false;
            state.submissions = action.payload;
        })
        .addCase(fetchAllSubmissions.rejected, (state, action) => {
            state.loading = false;
            state.error = action.payload;
        })
        .addCase(fetchSubmissionsByTaskId.pending, (state) => {
            state.loading = true;
        })
        .addCase(fetchSubmissionsByTaskId.fulfilled, (state, action) => {
            state.loading = false;
            state.submissions = action.payload;
        })
        .addCase(fetchSubmissionsByTaskId.rejected, (state, action) => {
            state.loading = false;
            state.error = action.payload;
        })
        .addCase(acceptOrDeclineSubmission.pending, (state) => {
            state.loading = true;
        })
        .addCase(acceptOrDeclineSubmission.fulfilled, (state, action) => {
            state.loading = false;
            state.submissions = state.submissions.map(submission => 
                submission.id === action.payload.id ? action.payload : submission
            );
        })
        .addCase(acceptOrDeclineSubmission.rejected, (state, action) => {
            state.loading = false;
            state.error = action.payload;
        })
    },
});

export default submissionSlice.reducer;