import { IPaginacao } from "../types/PaginacaoType";
import { IProdutos } from "../types/ProdutosType";
import { api } from "./ApiService";

export const retornarProduto = async (): Promise<IPaginacao<IProdutos>> => {
  const response = await api.get<IPaginacao<IProdutos>>(
    "/produtos/retornarProduto"
  );
  console.log(response.data)
  return response.data;
};
