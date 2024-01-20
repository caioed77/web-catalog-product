import { IProdutos } from "../types/ProdutosType";
import { api } from "./ApiService";

export const retornarProduto = async (): Promise<IProdutos[]> => {
  const response = await api.get<IProdutos[]>("/produtos/retornarProduto");
  return response.data;
};
