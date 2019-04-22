// Classe NFe
import WebmaniaBR.NFe;

public class NFeJava {

    public static void main(String[] args) throws Exception {

        // Emissão de Nota fiscal
        NFe nfe = new NFe();
        System.out.println(nfe.emitirNotaFiscal());
        
        // Consultar Status SEFAZ
        //NFe nfe = new NFe();
        //System.out.println(nfe.statusSefaz());
        
        // Consultar Validade Certificado A1
        //NFe nfe = new NFe();
        //System.out.println(nfe.validadeCertificadoA1());
        
        // Consultar Nota Fiscal
        //NFe nfe = new NFe();
        //System.out.println(nfe.consultarNotaFiscal());

    }
  
}

