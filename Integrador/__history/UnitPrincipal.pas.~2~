unit UnitPrincipal;

interface

uses
  Winapi.Windows, Winapi.Messages, System.SysUtils, System.Variants, System.Classes, Vcl.Graphics,
  Vcl.Controls, Vcl.Forms, Vcl.Dialogs, FireDAC.Stan.Intf, FireDAC.Stan.Option,
  FireDAC.Stan.Error, FireDAC.UI.Intf, FireDAC.Phys.Intf, FireDAC.Stan.Def,
  FireDAC.Phys, FireDAC.Stan.Pool, FireDAC.Stan.Async, FireDAC.VCLUI.Wait,
  FireDAC.Comp.Client, Data.DB, FireDAC.Phys.MySQL, FireDAC.Phys.MySQLDef,
  IdAuthentication, IdBaseComponent, IdComponent, IdTCPConnection, IdTCPClient,
  IdHTTP, Vcl.StdCtrls, Vcl.Grids, Vcl.DBGrids, Vcl.ExtCtrls, Datasnap.DBClient, System.JSON;

type
  TfrmPrincipal = class(TForm)
    IdHTTP1: TIdHTTP;
    CdsProduto: TClientDataSet;
    DataSource1: TDataSource;
    CdsProdutoID: TIntegerField;
    CdsProdutoDESCRICAO: TStringField;
    CdsProdutoPRECO: TFloatField;
    CdsProdutoIMAGEM: TStringField;
    CdsCores: TClientDataSet;
    IntegerField1: TIntegerField;
    StringField1: TStringField;
    StringField2: TStringField;
    CdsCoresProdutoID: TIntegerField;
    dsProduto: TDataSource;
    Panel1: TPanel;
    Panel2: TPanel;
    dbProduto: TDBGrid;
    Label1: TLabel;
    Panel3: TPanel;
    dbCores: TDBGrid;
    Label2: TLabel;
    procedure FormCreate(Sender: TObject);
  private
    procedure PreencheCDSProduto(const JSONString: string);
    { Private declarations }
  public
    { Public declarations }
  end;

var
  frmPrincipal: TfrmPrincipal;
  IdHTTP: TIdHTTP;
  Response: string;
  JSONObj: TJSONObject;
  CoresArray: TJSONArray;
  Cor: TJSONValue;

implementation

{$R *.dfm}


procedure TfrmPrincipal.FormCreate(Sender: TObject);
begin
  CdsProduto.CreateDataSet;
  IdHTTP := TIdHTTP.Create(nil);

  try
    Response := IdHTTP.Get('http://localhost:8080/produtos/listagemProdutos');
  finally
    IdHTTP.Free;
  end;

  PreencheCDSProduto(Response);
end;

procedure TfrmPrincipal.PreencheCDSProduto(const JSONString: string);
begin
  JSONObj := TJSONObject.ParseJSONValue(JSONString) as TJSONObject;

  try
    cdsProduto.EmptyDataSet;

    if not CdsProduto.Locate('ID', JSONObj.GetValue('id').Value, []) then
      cdsProduto.Append
    else
      cdsProduto.Edit;

    CdsProdutoID.Value := StrToInt(JSONObj.GetValue('id').Value);
    CdsProdutoDESCRICAO.Value := JSONObj.GetValue('descricao').Value;
    CdsProdutoPRECO.Value := StrToInt(JSONObj.GetValue('precoUnitario').Value);
    CdsProdutoIMAGEM.Value := JSONObj.GetValue('imagem').Value;
    cdsProduto.Post;

    cdsCores.EmptyDataSet;
    CoresArray := JSONObj.GetValue('cores') as TJSONArray;

    for Cor in CoresArray do
    begin
      cdsCores.Append;
      cdsCores.FieldByName('Cor').AsString := Cor.Value;
      cdsCores.Post;
    end;
  finally
    JSONObj.Free;
  end;
end;

end.
