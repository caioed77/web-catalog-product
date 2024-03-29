import axios, { AxiosResponse, InternalAxiosRequestConfig } from "axios";

export const api = axios.create({
  baseURL: "http:localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    return config;
  },
  (error): any => {
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (config: AxiosResponse<any, any>) => {
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
