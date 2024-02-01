object dmPrincipal: TdmPrincipal
  OnCreate = DataModuleCreate
  Height = 480
  Width = 640
  object cdsProduto: TClientDataSet
    Aggregates = <>
    FieldDefs = <>
    IndexDefs = <>
    IndexFieldNames = 'ID'
    Params = <>
    StoreDefs = True
    Left = 24
    Top = 16
    object cdsProdutoID: TIntegerField
      FieldName = 'ID'
    end
    object cdsProdutoDESCRICAO: TStringField
      FieldName = 'DESCRICAO'
      Size = 255
    end
    object cdsProdutoPRECO: TFloatField
      FieldName = 'PRECO'
    end
    object cdsProdutoIMAGEM: TStringField
      FieldName = 'IMAGEM'
      Size = 500
    end
  end
  object cdsCores: TClientDataSet
    Aggregates = <>
    FieldDefs = <>
    IndexDefs = <>
    Params = <>
    StoreDefs = True
    Left = 24
    Top = 80
    object cdsCoresID: TIntegerField
      FieldName = 'ID'
    end
    object cdsCoresDESCRICAO: TStringField
      FieldName = 'DESCRICAO'
      Size = 255
    end
    object cdsCoresIMAGEM: TStringField
      FieldName = 'IMAGEM'
      Size = 500
    end
    object cdsCoresPRODUTO_ID: TIntegerField
      FieldName = 'PRODUTO_ID'
    end
  end
  object dsCores: TDataSource
    DataSet = cdsCores
    Left = 80
    Top = 80
  end
  object dsProduto: TDataSource
    DataSet = cdsProduto
    Left = 80
    Top = 16
  end
end