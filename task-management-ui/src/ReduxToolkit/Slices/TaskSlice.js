import { createAsyncThunk, createSlice, isRejectedWithValue } from "@reduxjs/toolkit";
import { api, setAuthHeader } from "../../Api/api";

export const fetchTasks = createAsyncThunk("task/fetchTasks", async ({status}) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const { data } = await api.get(`/api/v1/tasks/`, { params: { status } });
        console.log("Fetch Task Success: ", data);
        return data;
    } catch (error) {
        console.log("Error: ", error);
        return isRejectedWithValue(error.message);
    }
});

export const fetchUsersTasks = createAsyncThunk("task/fetchUsersTasks", async ({status}) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const { data } = await api.get(`/api/v1/tasks/user`, { params: { status } });
        console.log("Fetch User Task Success: ", data);
        return data;
    } catch (error) {
        console.log("Error: ", error);
        return isRejectedWithValue(error.message);
    }
});

export const fetchTaskById = createAsyncThunk("task/fetchTaskById", async (id) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const { data } = await api.get(`/api/v1/tasks/${id}`);
        console.log("Fetch Task By Id Success: ", data);
        return data;
    } catch (error) {
        console.log("Error: ", error);
        return isRejectedWithValue(error.message);
    }
});

export const createTask = createAsyncThunk("task/createTask", async (task) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const { data } = await api.post(`/api/v1/tasks/`, task);
        console.log("Create Task Success: ", data);
        return data;
    } catch (error) {
        console.log("Error: ", error);
        return isRejectedWithValue(error.message);
    }
});

export const updateTask = createAsyncThunk("task/updateTask", async ({id,task}) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const { data } = await api.put(`/api/v1/tasks/${id}`, task);
        console.log("Update Task Success: ", data);
        return data;
    } catch (error) {
        console.log("Error: ", error);
        return isRejectedWithValue(error.message);
    }
});

export const assignTaskToUser = createAsyncThunk("task/assignTaskToUser", async ({id,userId}) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        const { data } = await api.put(`/api/v1/tasks/${id}/user/${userId}/assigned`);
        console.log("Assigned Task Success: ", data);
        return data;
    } catch (error) {
        console.log("Error: ", error);
        return isRejectedWithValue(error.message);
    }
});

export const deleteTask = createAsyncThunk("task/deleteTask", async (id) => {
    setAuthHeader(localStorage.getItem("jwt"), api);
    try {
        await api.delete(`/api/v1/tasks/${id}`);
        console.log("Delete Task Success: ", data);
        return id;
    } catch (error) {
        console.log("Error: ", error);
        return isRejectedWithValue(error.message);
    }
});

const taskSlice = createSlice({
    name: "task",
    initialState: {
        tasks: [],
        loading: false,
        error: null,
        taskDetailss: null,
        usersTasks: [],
    },
    reducers: {},
    extraReducers:(builder)=>{
        builder
        // Fetch Tasks
        .addCase(fetchTasks.pending, (state)=>{
            state.loading = true;
            state.error = null;
        })
        .addCase(fetchTasks.fulfilled, (state, action)=>{
            state.loading = false;
            state.tasks = action.payload;
        })
        .addCase(fetchTasks.rejected, (state, action)=>{
            state.loading = false;
            state.error = action.payload;
        })

        // Fetch User Tasks
        .addCase(fetchUsersTasks.pending, (state)=>{
            state.loading = true;
            state.error = null;
        })
        .addCase(fetchUsersTasks.fulfilled, (state, action)=>{
            state.loading = false;
            state.usersTasks = action.payload;
        })
        .addCase(fetchUsersTasks.rejected, (state, action)=>{
            state.loading = false;
            state.error = action.payload;
        })

        // Create Task
        .addCase(createTask.pending, (state)=>{
            state.loading = true;
            state.error = null;
        })
        .addCase(createTask.fulfilled, (state, action)=>{
            state.loading = false;
            state.tasks.push(action.payload);
        })
        .addCase(createTask.rejected, (state, action)=>{
            state.loading = false;
            state.error = action.payload;
        })

        // Update Task
        .addCase(updateTask.pending, (state)=>{
            state.loading = true;
            state.error = null;
        })
        .addCase(updateTask.fulfilled, (state, action)=>{
            const updatedTask = action.payload;
            state.loading = false;
            state.tasks = state.tasks.map((task)=>
                task.id === action.payload.id ? {...task, ...updatedTask} : task);
        })
        .addCase(updateTask.rejected, (state, action)=>{
            state.loading = false;
            state.error = action.payload;
        })

        // Assign Task To User
        .addCase(assignTaskToUser.pending, (state)=>{
            state.loading = true;
            state.error = null;
        })
        .addCase(assignTaskToUser.fulfilled, (state, action)=>{
            const updatedTask = action.payload;
            state.loading = false;
            state.tasks = state.tasks.map((task)=>
                task.id === action.payload.id ? {...task, ...updatedTask} : task);
        })
        .addCase(assignTaskToUser.rejected, (state, action)=>{
            state.loading = false;
            state.error = action.payload;
        })

        // Delete Task
        .addCase(deleteTask.pending, (state)=>{
            state.loading = true;
            state.error = null;
        })
        .addCase(deleteTask.fulfilled, (state, action)=>{
            state.loading = false;
            state.tasks = state.tasks.filter((task)=>task.id !== action.payload);
        })
        .addCase(deleteTask.rejected, (state, action)=>{
            state.loading = false;
            state.error = action.payload;
        })

        // Fetch Task By Id
        .addCase(fetchTaskById.pending, (state)=>{
            state.loading = true;
            state.error = null;
        })
        .addCase(fetchTaskById.fulfilled, (state, action)=>{
            state.loading = false;
            state.taskDetailss = action.payload;
        })
        .addCase(fetchTaskById.rejected, (state, action)=>{
            state.loading = false;
            state.error = action.payload;
        })
    }
});

export default taskSlice.reducer;
