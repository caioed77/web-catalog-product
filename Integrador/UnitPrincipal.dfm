object frmPrincipal: TfrmPrincipal
  Left = 0
  Top = 0
  Caption = 'Integrador catalogo'
  ClientHeight = 587
  ClientWidth = 1027
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -12
  Font.Name = 'Segoe UI'
  Font.Style = []
  OnCreate = FormCreate
  TextHeight = 15
  object Panel1: TPanel
    Left = 0
    Top = 0
    Width = 1027
    Height = 89
    Align = alTop
    TabOrder = 0
    ExplicitWidth = 1023
    object Label1: TLabel
      Left = 8
      Top = 32
      Width = 107
      Height = 37
      Caption = 'Produtos'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -27
      Font.Name = 'Segoe UI'
      Font.Style = []
      ParentFont = False
    end
    object Label2: TLabel
      Left = 560
      Top = 32
      Width = 67
      Height = 37
      Caption = 'Cores'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -27
      Font.Name = 'Segoe UI'
      Font.Style = []
      ParentFont = False
    end
    object btnGravarProduto: TBitBtn
      Left = 413
      Top = 46
      Width = 75
      Height = 25
      Caption = 'Gravar'
      TabOrder = 0
      OnClick = btnGravarProdutoClick
    end
  end
  object Panel2: TPanel
    Left = 0
    Top = 89
    Width = 489
    Height = 498
    Align = alLeft
    Alignment = taLeftJustify
    TabOrder = 1
    ExplicitHeight = 497
    object dbProduto: TDBGrid
      Left = 1
      Top = 1
      Width = 487
      Height = 496
      Align = alClient
      DataSource = dsProduto
      TabOrder = 0
      TitleFont.Charset = DEFAULT_CHARSET
      TitleFont.Color = clWindowText
      TitleFont.Height = -12
      TitleFont.Name = 'Segoe UI'
      TitleFont.Style = []
    end
  end
  object Panel3: TPanel
    Left = 560
    Top = 89
    Width = 467
    Height = 498
    Align = alRight
    Caption = 'Panel3'
    TabOrder = 2
    ExplicitLeft = 556
    ExplicitHeight = 497
    object dbCores: TDBGrid
      Left = 1
      Top = 1
      Width = 465
      Height = 496
      Align = alClient
      DataSource = dsCores
      TabOrder = 0
      TitleFont.Charset = DEFAULT_CHARSET
      TitleFont.Color = clWindowText
      TitleFont.Height = -12
      TitleFont.Name = 'Segoe UI'
      TitleFont.Style = []
    end
  end
  object IdHTTP1: TIdHTTP
    ProxyParams.BasicAuthentication = False
    ProxyParams.ProxyPort = 0
    Request.ContentLength = -1
    Request.ContentRangeEnd = -1
    Request.ContentRangeStart = -1
    Request.ContentRangeInstanceLength = -1
    Request.Accept = 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8'
    Request.BasicAuthentication = False
    Request.UserAgent = 'Mozilla/3.0 (compatible; Indy Library)'
    Request.Ranges.Units = 'bytes'
    Request.Ranges = <>
    HTTPOptions = [hoForceEncodeParams]
    Left = 72
    Top = 48
  end
  object CdsProduto: TClientDataSet
    Aggregates = <>
    Params = <>
    Left = 80
    Top = 120
    object CdsProdutoID: TIntegerField
      FieldName = 'ID'
    end
    object CdsProdutoDESCRICAO: TStringField
      FieldName = 'DESCRICAO'
    end
    object CdsProdutoPRECO: TFloatField
      FieldName = 'PRECO'
    end
    object CdsProdutoIMAGEM: TStringField
      FieldName = 'IMAGEM'
    end
  end
  object dsCores: TDataSource
    DataSet = CdsCores
    Left = 192
    Top = 200
  end
  object CdsCores: TClientDataSet
    Aggregates = <>
    Params = <>
    Left = 80
    Top = 192
    object CdsCoresID: TIntegerField
      FieldName = 'ID'
    end
    object CdsCoresDESCRICAO: TStringField
      FieldName = 'DESCRICAO'
    end
    object CdsCoresIMAGEM: TStringField
      FieldName = 'IMAGEM'
    end
    object CdsCoresPRODUTO_ID: TIntegerField
      FieldName = 'PRODUTO_ID'
    end
  end
  object dsProduto: TDataSource
    DataSet = CdsProduto
    Left = 192
    Top = 128
  end
end
