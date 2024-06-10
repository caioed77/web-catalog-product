import axios, { InternalAxiosRequestConfig } from "axios";
import moment from "moment";
import { IAuth } from "../types/AuthType";
import { useErroStore } from "../store/ErrorStore";

export const cancelaRequisicao = (config: InternalAxiosRequestConfig<any>) => {
  const controller = new AbortController();
  const cfg = {
    ...config,
    signal: controller.signal,
  };
  controller.abort();
  return cfg;
};

export const dataValidadeTokenExpirou = (
  dataValidadeToken: string
): Boolean => {
  const dataToken = moment(dataValidadeToken).utc(false).milliseconds(0);
  const dataAtual = moment().utc(false).millisecond(0);
  const expirou = dataAtual.isAfter(dataToken) || dataAtual.isSame(dataToken);
  return expirou;
};

export const atualizaToken = async (
  tokenRefresh: string
): Promise<IAuth | undefined> => {
  const http = axios.create({
    baseURL: "http://localhost:8080",
  });

  const dados = { tokenRefresh };
  try {
    const response = await http.post("/api/v1/auth/refresh-token", dados);
    return response.data;
  } catch (error) {
    const erroStore = useErroStore();

    if (axios.isAxiosError(error)) {
      erroStore.addErro(error.response?.data.title);
    } else {
      erroStore.addErro((error as Error).message);
    }

    return undefined;
  }
};
