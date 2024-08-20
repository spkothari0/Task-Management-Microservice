
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