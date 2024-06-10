import axios, { AxiosResponse, InternalAxiosRequestConfig } from "axios";
import { IAuth } from "../types/AuthType";
import {
  atualizaToken,
  cancelaRequisicao,
  dataValidadeTokenExpirou,
} from "./TokenService";
import { useErroStore } from "../store/ErrorStore";
import { useLoadingStore } from "../store/LoadingStore";
import { useTokenStore } from "../store/TokenStore";
import router from "../router";

export const api = axios.create({
  baseURL: "http:localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  async (config: InternalAxiosRequestConfig) => {
    const dadosUsuarioStorage = localStorage.getItem("dadosUsuario");

    if (dadosUsuarioStorage !== "" && dadosUsuarioStorage !== null) {
      const dadosUsuario: IAuth = JSON.parse(dadosUsuarioStorage);

      if (dataValidadeTokenExpirou(dadosUsuario.accessToken)) {
        const dadosToken = await atualizaToken(dadosUsuario.refreshToken);

        if (dadosToken == undefined) {
          return cancelaRequisicao(config);
        }

        config.headers.Authorization = `Bearer ${dadosToken?.accessToken}`;
        localStorage.setItem("dadosUsuario", JSON.stringify(dadosToken));
      } else {
        config.headers.Authorization = `Bearer ${dadosUsuario.accessToken}`;
      }
    }

    const erroStore = useErroStore();
    const loadingStore = useLoadingStore();
    erroStore.addErro("");
    loadingStore.showLoading();
    return config;
  },
  (error): any => {
    const erroStore = useErroStore();
    if (axios.isAxiosError(error)) {
      erroStore.addErro(
        error.response?.data.title,
        error.response?.data.detail
      );
    } else {
      erroStore.addErro(error);
    }

    const loadingStore = useLoadingStore();
    loadingStore.hideLoading();
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (config: AxiosResponse<any, any>) => {
    const loadingStore = useLoadingStore();
    loadingStore.hideLoading();
    return config;
  },
  (error) => {
    const erroStore = useErroStore();
    if (axios.isCancel(error)) {
      const tokenStore = useTokenStore();
      tokenStore.exibeMensagem();
      return Promise.reject(error);
    } else if (axios.isAxiosError(error)) {
      if (error.response !== undefined) {
        erroStore.addErro(
          error.response.data.title,
          error.response?.data.detail
        );
      } else {
        erroStore.addErro(error.message);
      }
    } else {
      erroStore.addErro(error);
    }

    const loadingStore = useLoadingStore();
    loadingStore.hideLoading();
    if (error.response.status === 401) {
      router.push("/");
    }
    return Promise.reject(error);
  }
);
