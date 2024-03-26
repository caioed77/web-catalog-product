import asyncio
import flet as ft
import sqlalchemy
from sqlalchemy import create_engine, Column, Integer, String, Float
from sqlalchemy.orm import sessionmaker

Base = sqlalchemy.orm.declarative_base()

class Produto(Base):
    __tablename__ = 'produtos'
    id = Column(Integer, primary_key=True)
    nome = Column(String)
    descricao = Column(String)
    valor = Column(Float)
    imagem = Column(String)

engine = create_engine('sqlite:///produtos.db')
Base.metadata.create_all(engine)
Session = sessionmaker(bind=engine)
session = Session()

class ProdutoRepository:
    def __init__(self):
        self.populate_database()

    def populate_database(self):        
        produtos = [
            Produto(nome='Produto 1', descricao='Descrição do Produto 1', valor=10.99, imagem='imagem1.jpg'),
            Produto(nome='Produto 2', descricao='Descrição do Produto 2', valor=20.99, imagem='imagem2.jpg'),
            Produto(nome='Produto 3', descricao='Descrição do Produto 3', valor=30.99, imagem='imagem3.jpg')
        ]
        session.add_all(produtos)
        session.commit()

    def listar_produtos(self):
        return session.query(Produto).all()

    def obter_produto_por_indice(self, indice):
        return session.query(Produto).filter_by(id=indice).first()

async def apresentar_produtos(page: 'CustomPage'):
    produto_repository = ProdutoRepository()
    
    async def detalhes_produto(index):
        produto = produto_repository.obter_produto_por_indice(index + 1)
        await page.go_async(f"/detalhes/{index}")

    produtos = produto_repository.listar_produtos()

    for i, produto in enumerate(produtos):
        card = ft.Card(
            ft.Image(src=f"/imagens/{produto.imagem}", width=200, height=200),
            ft.Text(produto.nome, size=16, weight="bold"),
            ft.Text(produto.descricao),
            ft.Text(f"R$ {produto.valor:.2f}")
        )
        card.on_click(lambda e, index=i: asyncio.create_task(detalhes_produto(index)))
        await page.add_async(card)

async def detalhes_produto(page: 'CustomPage'):
    produto_repository = ProdutoRepository()

    async def cadastrar_produto(e):
        print("Cadastrar novo produto")

    index = int(page.route.split("/")[-1])
    produto = produto_repository.obter_produto_por_indice(index + 1)
    
    await page.add_async(
        ft.Card(
            ft.Image(src=f"/imagens/{produto.imagem}", width=300, height=300),
            ft.Text(produto.nome, size=24, weight="bold"),
            ft.Text(produto.descricao),
            ft.Text(f"R$ {produto.valor:.2f}"),
            ft.ElevatedButton("Cadastrar Novo Produto", on_click=cadastrar_produto)
        )
    )

class CustomPage(ft.Page):
    def __init__(self, conn, session_id):
        super().__init__(conn, session_id)

async def handle_page_session(page):
    if page.route in app_config:
        await app_config[page.route](page)

app_config = {
    "/": apresentar_produtos,
    "/detalhes/{index}": detalhes_produto
}

async def main():
    await ft.app_async(handle_page_session)

if __name__ == "__main__":
    asyncio.run(main())
