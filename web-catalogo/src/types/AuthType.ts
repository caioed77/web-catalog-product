export interface IAuthUser {
  email: string;
  senha: string;
  role?: string;
}

export interface IAuth {
  accessToken: string;
  refreshToken: string;
  user: IAuthUser;
}


export interface ILogin {
    email: string;
    senha: string;
}