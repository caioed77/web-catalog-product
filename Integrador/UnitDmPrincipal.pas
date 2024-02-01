unit UnitDmPrincipal;

interface

uses
  System.SysUtils, System.Classes, Data.DB, Datasnap.DBClient, System.JSON, IdHTTP, IdTCPConnection, IdTCPClient;

type
  TdmPrincipal = class(TDataModule)
    cdsProduto: TClientDataSet;
    cdsProdutoID: TIntegerField;
    cdsProdutoDESCRICAO: TStringField;
    cdsProdutoPRECO: TFloatField;
    cdsProdutoIMAGEM: TStringField;
    cdsCores: TClientDataSet;
    cdsCoresID: TIntegerField;
    cdsCoresDESCRICAO: TStringField;
    cdsCoresIMAGEM: TStringField;
    cdsCoresPRODUTO_ID: TIntegerField;
    dsCores: TDataSource;
    dsProduto: TDataSource;
    procedure DataModuleCreate(Sender: TObject);
    procedure BuscarProdutos(const JSONString: string);
    procedure GravarNovosProdutos;
    procedure GravarNovasCores;
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  dmPrincipal: TdmPrincipal;
  IdHTTP: TIdHTTP;

implementation

{%CLASSGROUP 'Vcl.Controls.TControl'}

uses UnitPrincipal;

{$R *.dfm}

procedure TdmPrincipal.BuscarProdutos(const JSONString: string);
var JSONArray: TJSONArray;
    JSONValue, JSONCorValue: TJSONValue;
    JSONObj, JSONObjCor: TJSONObject;
    CoresArray: TJSONArray;
    Cor: TJSONValue;
begin
  JSONArray := TJSONObject.ParseJSONValue(JSONString) as TJSONArray;

  try
    for JSONValue in JSONArray do
    begin
      if JSONValue is TJSONObject then
      begin
        JSONObj := JSONValue as TJSONObject;

        if not dmPrincipal.cdsProduto.FindKey([JSONObj.GetValue('id').Value]) then
          cdsProduto.Append
        else
          cdsProduto.Edit;

        cdsProdutoID.Value := StrToIntDef(JSONObj.GetValue('id').Value, 0);
        cdsProdutoDESCRICAO.Value := JSONObj.GetValue('descricao').Value;
        cdsProdutoPRECO.Value := StrToFloatDef(JSONObj.GetValue('precoUnitario').Value, 0.0);
        cdsProdutoIMAGEM.Value := JSONObj.GetValue('imagem').Value;
        cdsProduto.Post;

        cdsCores.EmptyDataSet;
        CoresArray := JSONObj.GetValue('cores') as TJSONArray;

        for JSONCorValue in CoresArray do
        begin
          if JSONCorValue is TJSONObject then
          begin
            JSONObjCor := JSONCorValue as TJSONObject;
          
            cdsCores.Append;
            cdsCoresID.Value := StrToIntDef(JSONObjCor.GetValue('id').Value, 0);
            cdsCoresDESCRICAO.Value := JSONObjCor.GetValue('descricao').Value;
            cdsCoresIMAGEM.Value := JSONObjCor.GetValue('imagem').Value;
            cdsCoresPRODUTO_ID.Value := cdsProdutoID.Value; 
            cdsCores.Post;
          end;
        end;
      end;
    end;
  finally
    JSONArray.Free;
  end;
end;

procedure TdmPrincipal.DataModuleCreate(Sender: TObject);
begin
  cdsProduto.CreateDataSet;
  cdsCores.CreateDataSet;
end;

procedure TdmPrincipal.GravarNovasCores;
var JSONArrayCores: TJSONArray;
    JSONToSend: TJSONObject;
    JSONCor: TJSONObject;
    JSONString: string;
    Stream: TStringStream;
begin
  IdHTTP := TIdHTTP.Create(nil);
  JSONToSend := TJSONObject.Create;
  JSONArrayCores := TJSONArray.Create;
  JSONArrayCores := TJSONArray.Create;

  JSONCor := TJSONObject.Create;
  if not dmPrincipal.cdsCores.Locate('id', cdsCoresID.Value, []) then
    cdsCores.Append
  else
    cdsCores.Edit;

  JSONCor.AddPair('id', IntToStr(cdsCoresID.Value));
  JSONCor.AddPair('descricao', cdsCoresDESCRICAO.Value);
  JSONCor.AddPair('imagem', cdsCoresIMAGEM.Value);
  JSONArrayCores.AddElement(JSONCor);

  Stream := TStringStream.Create(JSONArrayCores.Items[0].ToString, TEncoding.UTF8);

  try
    IdHTTP.Request.ContentType := 'application/json';
    IdHTTP.Post(urlGravarProduto, Stream);
  finally
    Stream.Free;
  end;
end;

procedure TdmPrincipal.GravarNovosProdutos;
var
  JSONToSend: TJSONObject;
  JSONArrayProdutos: TJSONArray;
  JSONProduto: TJSONObject;
  JSONString: string;
  Stream: TStringStream;
begin
  IdHTTP := TIdHTTP.Create(nil);
  JSONToSend := TJSONObject.Create;
  JSONArrayProdutos := TJSONArray.Create;

  if not CdsProduto.Locate('id', cdsProdutoID.Value, []) then
    cdsProduto.Append
  else
    cdsProduto.Edit;

  JSONProduto := TJSONObject.Create;
  JSONProduto.AddPair('descricao', cdsProdutoDESCRICAO.Value);
  JSONProduto.AddPair('precoUnitario', cdsProdutoPRECO.Value);
  JSONProduto.AddPair('imagem', cdsProdutoIMAGEM.Value);


  JSONArrayProdutos.AddElement(JSONProduto);
  Stream := TStringStream.Create(JSONArrayProdutos.Items[0].ToString, TEncoding.UTF8);

  try
    IdHTTP.Request.ContentType := 'application/json';
    IdHTTP.Post(urlGravarProduto, Stream);
  finally
    Stream.Free;
  end;
end;

end.
