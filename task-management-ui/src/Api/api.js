import axios from "axios";
import { v4 as uuidv4 } from 'uuid';

export const baseUrl="http://localhost:5000";

export const api = axios.create({
    baseURL: baseUrl,
    headers: {
        "Content-Type": "application/json",
    },
});

export const setAuthHeader = (token, api) => {
    if(token)
        api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    else
        delete api.defaults.headers.common["Authorization"];
}

// Add a request interceptor to include the correlation ID
api.interceptors.request.use((config) => {
    // Generate a new UUID for the correlation ID
    const correlationId = uuidv4();

    // Attach the correlation ID to the headers
    config.headers['Correlation-Id'] = correlationId;

    return config;
}, (error) => {
    // Handle the error
    return Promise.reject(error);
});