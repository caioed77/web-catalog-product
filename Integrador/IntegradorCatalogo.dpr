program IntegradorCatalogo;

uses
  Vcl.Forms,
  UnitPrincipal in 'UnitPrincipal.pas' {frmPrincipal},
  UnitDmPrincipal in 'UnitDmPrincipal.pas' {dmPrincipal: TDataModule};

{$R *.res}

begin
  Application.Initialize;
  Application.MainFormOnTaskbar := True;
  Application.CreateForm(TfrmPrincipal, frmPrincipal);
  Application.CreateForm(TdmPrincipal, dmPrincipal);
  Application.Run;
end.
