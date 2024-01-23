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
  System.Win.TaskbarCore, Vcl.Taskbar;

type
  TfrmPrincipal = class(TForm)
    IdHTTP1: TIdHTTP;
    CdsProduto: TClientDataSet;
    dsCores: TDataSource;
    CdsProdutoID: TIntegerField;
    CdsProdutoDESCRICAO: TStringField;
    CdsProdutoPRECO: TFloatField;
    CdsProdutoIMAGEM: TStringField;
    CdsCores: TClientDataSet;
    dsProduto: TDataSource;
    Panel1: TPanel;
    Label1: TLabel;
    CdsCoresID: TIntegerField;
    CdsCoresDESCRICAO: TStringField;
    CdsCoresIMAGEM: TStringField;
    CdsCoresPRODUTO_ID: TIntegerField;
    Paginas: TPageControl;
    Review: TTabSheet;
    Panel2: TPanel;
    dbProduto: TDBGrid;
    Panel3: TPanel;
    dbCores: TDBGrid;
    Cadastro: TTabSheet;
    Label2: TLabel;
    Label3: TLabel;
    Panel4: TPanel;
    btnGravarProduto: TBitBtn;
    Edit1: TEdit;
    Edit2: TEdit;
    OpenDialog: TOpenDialog;
    btnImagem: TBitBtn;
    edtURLImagem: TEdit;
    Label4: TLabel;
    Panel5: TPanel;
    Label5: TLabel;
    Label6: TLabel;
    procedure FormCreate(Sender: TObject);
    procedure btnGravarProdutoClick(Sender: TObject);
    procedure btnImagemClick(Sender: TObject);
  private
    procedure PreencheCDSProduto(const JSONString: string);
    procedure GravarProdutos;
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
  const urlListaProduto = 'http://localhost:8080/produtos/listagemProdutos';
  const urlGravarProduto = 'http://localhost:8080/produtos/gravarProduto';

implementation

{$R *.dfm}


procedure TfrmPrincipal.btnImagemClick(Sender: TObject);
begin
  if OpenDialog.Execute then
  begin
    edtURLImagem.Text := OpenDialog.FileName;

    CdsProduto.Edit;
    CdsProdutoIMAGEM.Value := edtURLImagem.Text;
  end;
end;

procedure TfrmPrincipal.FormCreate(Sender: TObject);
begin
  CdsProduto.CreateDataSet;
  CdsCores.CreateDataSet;

  IdHTTP := TIdHTTP.Create(nil);

  try
    Response := IdHTTP.Get(urlListaProduto);
  finally
    IdHTTP.Free;
  end;

  PreencheCDSProduto(Response);
end;

procedure TfrmPrincipal.GravarProdutos;
var
  JSONToSend: TJSONObject;
  JSONArrayProdutos, JSONArrayCores: TJSONArray;
  JSONProduto, JSONCor: TJSONObject;
  JSONString: string;
  Stream: TStringStream;

begin
  IdHTTP := TIdHTTP.Create(nil);
  JSONToSend := TJSONObject.Create;
  JSONArrayProdutos := TJSONArray.Create;
  JSONArrayCores := TJSONArray.Create;

  try
    cdsProduto.First;

    while not cdsProduto.Eof do
    begin
      JSONProduto := TJSONObject.Create;
      JSONProduto.AddPair('id', IntToStr(cdsProdutoID.Value));
      JSONProduto.AddPair('descricao', cdsProdutoDESCRICAO.Value);
      JSONProduto.AddPair('precoUnitario', FloatToStr(cdsProdutoPRECO.Value));
      JSONProduto.AddPair('imagem', cdsProdutoIMAGEM.Value);

      cdsCores.Filter := 'PRODUTO_ID = ' + IntToStr(cdsProdutoID.Value);

      cdsCores.First;
      while not cdsCores.Eof do
      begin
        JSONCor := TJSONObject.Create;
        JSONCor.AddPair('id', IntToStr(CdsCoresID.Value));
        JSONCor.AddPair('descricao', cdsCoresDESCRICAO.Value);
        JSONCor.AddPair('imagem', cdsCoresIMAGEM.Value);
        JSONCor.AddPair('produto_id', CdsCoresPRODUTO_ID.Value);
        JSONArrayCores.AddElement(JSONCor);

        cdsCores.Next;
      end;

      JSONProduto.AddPair('cores', JSONArrayCores);
      JSONArrayProdutos.AddElement(JSONProduto);

      cdsProduto.Next;
    end;


    Stream := TStringStream.Create(JSONArrayProdutos.Items[0].ToString, TEncoding.UTF8);

    ShowMessage(Stream.ToString);
     try
      IdHTTP.Request.ContentType := 'application/json';
      IdHTTP.Post(urlGravarProduto, Stream);
    finally
      Stream.Free;
    end;

  finally
    IdHTTP.Free;
    JSONToSend.Free;
  end;
end;

procedure TfrmPrincipal.PreencheCDSProduto(const JSONString: string);
var JSONArray: TJSONArray;
    JSONValue: TJSONValue;
    JSONObj: TJSONObject;
    CoresArray: TJSONArray;
    Cor: TJSONValue;
begin
  JSONArray := TJSONObject.ParseJSONValue(JSONString) as TJSONArray;

  try
    cdsProduto.EmptyDataSet;

    for JSONValue in JSONArray do
    begin
      if JSONValue is TJSONObject then
      begin
        JSONObj := JSONValue as TJSONObject;

        if not CdsProduto.Locate('ID', JSONObj.GetValue('id').Value, []) then
          cdsProduto.Append
        else
          cdsProduto.Edit;

        CdsProdutoID.Value := StrToIntDef(JSONObj.GetValue('id').Value, 0);
        CdsProdutoDESCRICAO.Value := JSONObj.GetValue('descricao').Value;
        CdsProdutoPRECO.Value := StrToFloatDef(JSONObj.GetValue('precoUnitario').Value, 0.0);
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
      end;
    end;
  finally
    JSONArray.Free;
  end;
end;

procedure TfrmPrincipal.btnGravarProdutoClick(Sender: TObject);
begin
  GravarProdutos;
end;

end.
