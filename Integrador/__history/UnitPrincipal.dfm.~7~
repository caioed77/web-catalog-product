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
  Position = poDesktopCenter
  OnCreate = FormCreate
  TextHeight = 15
  object Panel1: TPanel
    Left = 0
    Top = 0
    Width = 1027
    Height = 73
    Align = alTop
    TabOrder = 0
    ExplicitWidth = 1023
    object Label1: TLabel
      Left = 13
      Top = 11
      Width = 255
      Height = 37
      Caption = 'Controle de produtos'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -27
      Font.Name = 'Segoe UI'
      Font.Style = []
      ParentFont = False
    end
  end
  object Paginas: TPageControl
    Left = 0
    Top = 73
    Width = 1027
    Height = 514
    ActivePage = Review
    Align = alClient
    TabOrder = 1
    object Review: TTabSheet
      Caption = 'Review'
      object Panel2: TPanel
        Left = 0
        Top = 41
        Width = 489
        Height = 443
        Align = alLeft
        Alignment = taLeftJustify
        TabOrder = 0
        object dbProduto: TDBGrid
          Left = 1
          Top = 1
          Width = 487
          Height = 441
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
        Left = 520
        Top = 41
        Width = 499
        Height = 443
        Align = alRight
        Caption = 'Panel3'
        TabOrder = 1
        object dbCores: TDBGrid
          Left = 1
          Top = 1
          Width = 497
          Height = 441
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
      object Panel5: TPanel
        Left = 0
        Top = 0
        Width = 1019
        Height = 41
        Align = alTop
        TabOrder = 2
        object Label5: TLabel
          Left = 208
          Top = 14
          Width = 69
          Height = 21
          Caption = 'Produtos'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWindowText
          Font.Height = -16
          Font.Name = 'Segoe UI'
          Font.Style = [fsBold]
          ParentFont = False
        end
        object Label6: TLabel
          Left = 752
          Top = 14
          Width = 42
          Height = 21
          Caption = 'Cores'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWindowText
          Font.Height = -16
          Font.Name = 'Segoe UI'
          Font.Style = [fsBold]
          ParentFont = False
        end
      end
    end
    object Cadastro: TTabSheet
      Caption = 'Cadastro'
      ImageIndex = 1
      object Label2: TLabel
        Left = 24
        Top = 87
        Width = 140
        Height = 21
        Caption = 'Descri'#231#227'o produto'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -16
        Font.Name = 'Segoe UI'
        Font.Style = [fsBold]
        ParentFont = False
      end
      object Label3: TLabel
        Left = 24
        Top = 144
        Width = 108
        Height = 21
        Caption = 'Pre'#231'o unitario'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -16
        Font.Name = 'Segoe UI'
        Font.Style = [fsBold]
        ParentFont = False
      end
      object Label4: TLabel
        Left = 192
        Top = 141
        Width = 63
        Height = 21
        Caption = 'Imagem'
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -16
        Font.Name = 'Segoe UI'
        Font.Style = [fsBold]
        ParentFont = False
      end
      object Panel4: TPanel
        Left = 0
        Top = 0
        Width = 1019
        Height = 65
        Align = alTop
        TabOrder = 0
        object btnGravarProduto: TBitBtn
          Left = 229
          Top = 25
          Width = 75
          Height = 25
          Caption = 'Gravar'
          TabOrder = 0
          OnClick = btnGravarProdutoClick
        end
      end
      object Edit1: TEdit
        Left = 24
        Top = 110
        Width = 353
        Height = 23
        TabOrder = 1
      end
      object Edit2: TEdit
        Left = 24
        Top = 168
        Width = 140
        Height = 23
        TabOrder = 2
      end
      object btnImagem: TBitBtn
        Left = 316
        Top = 167
        Width = 41
        Height = 25
        Caption = 'T'
        TabOrder = 3
        OnClick = btnImagemClick
      end
      object edtURLImagem: TEdit
        Left = 192
        Top = 168
        Width = 118
        Height = 23
        TabOrder = 4
      end
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
    Left = 152
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
  object OpenDialog: TOpenDialog
    Left = 412
    Top = 235
  end
end
