unit UnitPrincipal;

interface

uses
  Winapi.Windows, Winapi.Messages, System.SysUtils, System.Variants, System.Classes, Vcl.Graphics,
  Vcl.Controls, Vcl.Forms, Vcl.Dialogs, FireDAC.Stan.Intf, FireDAC.Stan.Option,
  FireDAC.Stan.Error, FireDAC.UI.Intf, FireDAC.Phys.Intf, FireDAC.Stan.Def,
  FireDAC.Phys, FireDAC.Stan.Pool, FireDAC.Stan.Async, FireDAC.VCLUI.Wait,
  FireDAC.Comp.Client, Data.DB, FireDAC.Phys.MySQL, FireDAC.Phys.MySQLDef,
  IdAuthentication, IdBaseComponent, IdComponent, IdTCPConnection, IdTCPClient,
  IdHTTP, Vcl.StdCtrls, Vcl.Grids, Vcl.DBGrids, Vcl.ExtCtrls, Datasnap.DBClient, System.JSON,
  Vcl.Buttons, Vcl.ComCtrls, Vcl.CustomizeDlg, System.ImageList, Vcl.ImgList,
  System.Win.TaskbarCore, Vcl.Taskbar, Vcl.DBCtrls, Vcl.Mask, UnitDmPrincipal;

type
  TfrmPrincipal = class(TForm)
    IdHTTP1: TIdHTTP;
    Panel1: TPanel;
    Label1: TLabel;
    Paginas: TPageControl;
    Review: TTabSheet;
    Panel2: TPanel;
    dbProduto: TDBGrid;
    Produtos: TTabSheet;
    Panel4: TPanel;
    OpenDialog: TOpenDialog;
    Panel5: TPanel;
    Label5: TLabel;
    RadioGroup1: TRadioGroup;
    Label2: TLabel;
    Label3: TLabel;
    Label4: TLabel;
    Navegador: TDBNavigator;
    btnSalvar: TSpeedButton;
    btnDeletar: TSpeedButton;
    edtDescricaoProduto: TDBEdit;
    edtImagemProduto: TDBEdit;
    edtPrecoUnit: TDBEdit;
    btnNovoProduto: TSpeedButton;
    Panel3: TPanel;
    dbCores: TDBGrid;
    Cores: TTabSheet;
    btnImagemCor: TSpeedButton;
    RadioGroup2: TRadioGroup;
    Label9: TLabel;
    edtDescricaoCor: TDBEdit;
    edtImagemCor: TDBEdit;
    Label7: TLabel;
    btnImagemProduto: TSpeedButton;
    Label6: TLabel;
    btnNovaCor: TSpeedButton;
    procedure FormCreate(Sender: TObject);
    procedure btnImagemProdutoClick(Sender: TObject);
    procedure btnSalvarClick(Sender: TObject);
    procedure btnImagemCorClick(Sender: TObject);
    procedure btnNovoProdutoClick(Sender: TObject);
    procedure btnNovaCorClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  frmPrincipal: TfrmPrincipal;
  IdHTTPGet: TIdHTTP;
  Response: string;
  JSONObj: TJSONObject;
  CoresArray: TJSONArray;
  Cor: TJSONValue;
  const urlListaProduto = 'http://localhost:8080/produtos/listagemProdutos';
  const urlGravarProduto = 'http://localhost:8080/produtos/gravarProduto';

implementation

{$R *.dfm}


procedure TfrmPrincipal.btnSalvarClick(Sender: TObject);
begin
  dmPrincipal.GravarNovosProdutos;
end;

procedure TfrmPrincipal.FormCreate(Sender: TObject);
begin
  IdHTTPGet := TIdHTTP.Create(nil);

  try
    Response := IdHTTPGet.Get(urlListaProduto);
  finally
    IdHTTPGet.Free;
  end;

  dmPrincipal := TdmPrincipal.Create(Self);

  dmPrincipal.BuscarProdutos(Response);
end;

procedure TfrmPrincipal.btnImagemCorClick(Sender: TObject);
begin
  if OpenDialog.Execute then
    edtImagemProduto.Text := OpenDialog.FileName;
end;

procedure TfrmPrincipal.btnImagemProdutoClick(Sender: TObject);
begin
  if OpenDialog.Execute then
    edtImagemCor.Text := OpenDialog.FileName;
end;

procedure TfrmPrincipal.btnNovaCorClick(Sender: TObject);
begin
  dmPrincipal.cdsCores.Append;
  Paginas.ActivePageIndex := 2;
end;

procedure TfrmPrincipal.btnNovoProdutoClick(Sender: TObject);
begin
  dmPrincipal.cdsProduto.Insert;
end;

end.
