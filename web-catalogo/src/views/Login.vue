<template>
  <div
    class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8 mt-10"
  >
    <div class="sm:mx-auto sm:w-full sm:max-w-sm">
      <h2
        class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-black"
      >
        Faça login em sua conta
      </h2>
    </div>

    <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
      <form class="space-y-6" action="#" method="POST">
        <div>
          <label
            for="email"
            class="block text-sm font-bold leading-6 text-black"
            >Email</label
          >
          <div class="mt-2">
            <input
              id="email"
              name="email"
              type="email"
              autocomplete="email"
              required="true"
              class="block px-5 w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>

        <div>
          <div class="flex items-center justify-between">
            <label
              for="password"
              class="block text-sm font-bold leading-6 text-black"
              >Senha</label
            >
            <div class="text-sm">
              <a
                href="#"
                class="font-bold text-indigo-600 hover:text-indigo-500"
                >Esqueci minha senha?</a
              >
            </div>
          </div>
          <div class="mt-2">
            <input
              id="password"
              name="password"
              type="password"
              autocomplete="current-password"
              required="true"
              class="block px-5 w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>

        <div>
          <button
            type="submit"
            class="flex w-full justify-center px-5 rounded-md bg-indigo-600 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            @click="onLogin()"
          >
            Entrar
          </button>
        </div>
      </form>

      <p class="mt-10 text-center text-sm text-black">
        não possui conta?
        {{ " " }}
        <a
          href="/#/Cadastro"
          class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500"
          >cadastrar-me</a
        >
      </p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import { authenticated } from "../services/AuthService";
import { useUsuarioStore } from "../store/UsuarioStore";
import router from "../router";
import { IAuth } from "../types/AuthType";

const usuarioStore = useUsuarioStore();

const dadosLogin = reactive<IAuth>({
  accessToken: "",
  refreshToken: "",
  user: {
    email: "",
    senha: "",
    role: "",
  },
});

//const validaDadosLogin = (): boolean => {
//dadosLogin.user.email = "";
// dadosLogin.user.senha = "";
//  return false;
//};

async function onLogin() {
  const usuarioLogado = await authenticated(dadosLogin);

  if (usuarioLogado.accessToken !== "") {
    console.log("Ok");
    usuarioStore.atualizaDadosUsuario(usuarioLogado);
    router.push("/dashboard/produtos");
  }

  console.log("Não passou");
}
</script>
