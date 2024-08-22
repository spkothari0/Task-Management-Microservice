export const handleApiResponse = (response) => {
    const { statusCode, data, message, errors } = response;

    if (statusCode >= 200 && statusCode < 300) {
        return data;
    } else {
        console.error("API Error: ", message, errors);
        throw new Error(errors);
    }
};