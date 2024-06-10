import { defineStore } from "pinia";
import { reactive } from "vue";
import { IAuth } from "../types/AuthType";

export const useUsuarioStore = defineStore("useUsuarioStore", () => {
  const usuario = reactive<IAuth>({
    accessToken: "",
    refreshToken: "",
    user: {
      email: "",
      senha: "",
    },
  });
  const usuarioLogado = localStorage.getItem("usuarioLogado");

  if (usuarioLogado !== null && usuarioLogado !== "") {
    const dadosUsuarioLogado: IAuth = JSON.parse(usuarioLogado);
    usuario.accessToken = dadosUsuarioLogado.accessToken;
    usuario.refreshToken = dadosUsuarioLogado.refreshToken;
    usuario.user = dadosUsuarioLogado.user;
  }

  function atualizaDadosUsuario(novoUsuario: IAuth) {
    usuario.accessToken = novoUsuario.accessToken;
    usuario.refreshToken = novoUsuario.refreshToken;
    usuario.user = novoUsuario.user;
    localStorage.setItem("usuarioLogado", JSON.stringify(novoUsuario));
  };

  function logout () {
    usuario.accessToken = "";
    usuario.refreshToken = "";
    usuario.user.email = ""
    usuario.accessToken = ""
    usuario.refreshToken = ""
    localStorage.setItem("usuarioLogado", "");   
  };

  return { usuario, atualizaDadosUsuario, logout };
});
