<p align="center">
  <img src="https://wmbr.s3.amazonaws.com/img/logo_webmaniabr_github.png">
</p>

# NFe-Java

Através do emissor de Nota Fiscal da WebmaniaBR®, você conta com a emissão e arquivamento das suas notas fiscais, cálculo automático de impostos, geração do Danfe para impressão e envio automático de e-mails para os clientes. Realize a integração com o seu sistema utilizando a nossa REST API.

- Emissor de Nota Fiscal WebmaniaBR®: [Saiba mais](https://webmaniabr.com/nota-fiscal-eletronica/)
- Documentação REST API: [Visualizar](https://webmaniabr.com/docs/rest-api-nfe/)

## Requisitos

- Contrate um dos planos de Nota Fiscal Eletrônica da WebmaniaBR® a partir de R$34,90/mês: [Assine agora mesmo](https://webmaniabr.com/nota-fiscal-eletronica/).
- Instalar as bibliotecas [org.JSON](https://github.com/stleary/JSON-java) e [org.json.simple.parser.JSONParser](https://jar-download.com/maven-repository-class-search.php?search_box=%20org.json.simple.parser.JSONParser).
- Realize a integração com o seu sistema.

## Estrutura

- **NFeJava.java**: Classe principal para execução do programa.
- **WebmaniaBR/NFe.java**: Classe com todos os métodos, como Emissão de Nota Fiscal, Consultar Status SEFAZ, Validar Certificado A1, entre outros.
- **WebmaniaBR/config/Credenciais.java**: Classe para configuração de credenciais de acesso e também para o tratamento de conexão com a API.
- **WebmaniaBR/ExemploJSON/**: Diretório com exemplos de JSON para ser enviado a API.

## Exemplos

Desenvolvimento baseado na classe [HttpURLConnection](https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html).

Os exemplos abaixo são os métodos da classe NFe.

- **cancelarNotaFiscal**: Cancelar Nota Fiscal enviada ao SEFAZ.
- **cartaCorrecao**: Corrigir uma Nota Fiscal junto ao SEFAZ.
- **consultarNotaFiscal**: Consulta a Nota Fiscal enviada para o SEFAZ.
- **emitirNotaFiscal_Armamentos**: Emissão da Nota Fiscal com detalhamento específico de Armamentos.
- **emitirNotaFiscal_Combustivel**: Emissão da Nota Fiscal com detalhamento específico de Combustivel.
- **emitirNotaFiscal_Medicamentos**: Emissão da Nota Fiscal com detalhamento específico de Medicamentos.
- **emitirNotaFiscal_Rastreabilidade**: Emissão da Nota Fiscal com detalhamento específico de Rastreabilidade.
- **emitirNotaFiscal_VeiculosNovos**: Emissão da Nota Fiscal com detalhamento específico de Veiculos Novos.
- **emitirNotaFiscal**: Emissão da Nota Fiscal junto ao SEFAZ.
- **emitirNotaFiscalAjuste**: Emite uma nota fiscal de ajuste.
- **emitirNotaFiscalComplementar_Imposto**: Emite uma Nota Fiscal complementar.
- **emitirNotaFiscalComplementar_PrecoQuantidade**: Emite uma Nota Fiscal complementar.
- **emitirNotaFiscalDevolucao**: Emissão de Nota Fiscal de devolução junto ao SEFAZ.
- **inutilizarNumeracao**: Inutilizar sequência de numeração junto ao SEFAZ.
- **statusSefaz**: Verifica se o Sefaz está Online ou Offline.
- **validadeCertificadoA1**: Verifica se o Certificado A1 é válido e quantos dias faltam para expirar.

## Suporte

Qualquer dúvida entre em contato na nossa [Central de Ajuda](https://ajuda.webmaniabr.com) ou acesse o [Painel de Controle](https://webmaniabr.com/painel/) para conversar em tempo real no Chat ou Abrir um chamado.
