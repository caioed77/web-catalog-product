import { IAuth, IAuthUser } from "../types/AuthType";
import { api } from "./ApiService";

export async function criarUsuario(dados: IAuthUser) {
  await api.post<IAuthUser>("/api/v1/auth/register", dados);
}

export async function authenticated(dados: IAuth) {
   const response = await api.post<IAuth>("/api/v1/auth/authenticate" + dados)
    return response.data
}



