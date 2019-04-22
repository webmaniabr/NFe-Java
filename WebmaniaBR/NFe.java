package WebmaniaBR;

// Classe para configuração de credenciais de acesso
import WebmaniaBR.config.Credenciais;
 
// Demais importações
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NFe extends Credenciais{
    
    // Tramamento da resposta retornado pela API
    public Object trataRespostaApi(InputStreamReader input) throws IOException{
        
        try {
            
            // Recebe a resposta da API, trata e converte para ObjetoJson
            BufferedReader in = new BufferedReader(input);
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // Finaliza buffer
            input.close();

            // Objeto JSON para facilitar o tratamento dos dados.
            JSONObject json_input = new JSONObject(response.toString());

            return json_input;
            
        }catch(IOException e) {
            System.out.println("Erro: "+e);
        }
        
        return null;
        
    }
    
    /**
    * Cancelar Nota Fiscal
    *
    * Atenção: Somente poderá ser cancelada uma NF-e cujo uso tenha sido previamente
    * autorizado pelo Fisco e desde que não tenha ainda ocorrido o fato gerador, ou seja,
    * ainda não tenha ocorrido a saída da mercadoria do estabelecimento. Atualmente o prazo
    * máximo para cancelamento de uma NF-e é de 24 horas (1 dia), contado a partir da autorização
    * de uso. Caso já tenha passado o prazo de 24 horas ou já tenha ocorrido a circulação da
    * mercadoria, emita uma NF-e de devolução para anular a NF-e anterior.
    */
    public String cancelarNotaFiscal() throws IOException, ParseException {
        
        /*
        * FileReader lê o arquivo cancelarNotaFiscal.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/cancelarNotaFiscal.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/cancelar/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "PUT");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
     
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
           
            //Finaliza a conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.getString("status"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e);
            System.out.println("Erro: "+ con.getResponseMessage());
        }
        return null;
    }
    
    /**
    * Informações da Carta de Correção
    *
    * A Carta de Correção Eletrônica (CC-e) é um evento legal e tem por objetivo
    * corrigir algumas informações da NF-e que já foi emitida.
    */
    public String cartaCorrecao() throws IOException, ParseException {
        /*
        * FileReader lê o arquivo cartaCorrecao.json, caso os dados venha de um 
        * formulário ou banco de dados, passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/cartaCorrecao.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/cartacorrecao/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "POST");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
     
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
           
            //Finaliza a conexão
            con.disconnect();
            
            try{
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.getString("status"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e);
            System.out.println("Erro: "+ con.getResponseMessage());
        }
        
        return null;
    }
    
    /**
    * Consulta de Nota fiscal
    *
    * Atenção: Somente é permitido consultar a chave da nota fiscal emitida pelo
    * emissor da WebmaniaBR, não sendo possível consultar nota fiscal de terceiro
    * ou emitida em outro sistema.
    */
    public String consultarNotaFiscal() throws IOException, ParseException {
        
        // Atributos para consulta método GET
        String chave = "00000000000000000000000000000000000000000000";
        int ambiente = 2;

        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/consulta/?chave="+chave+"&ambiente="+ambiente);
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "GET");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
     
        try{
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
            
            //Finaliza a conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("status"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro: "+ json_input.getString("error"));
            }
            
        }catch(IOException e){
            System.out.println("Erro: "+e);
            System.out.println("Erro: "+ con.getResponseMessage());
        }
        
        return null; 
    }
    
    // Emissão de Nota Fiscal Eletrônica com detalhamento específico de armamentos
    public String emitirNotaFiscal_Armamentos() throws IOException, ParseException {
        /*
        * FileReader lê o arquivo emitirNotaFiscal_Armamentos.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/emitirNotaFiscal_Armamentos.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/emissao/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "POST");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
        
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
            
            // Finaliza conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("chave"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro retornado da API: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e.getMessage());
            System.out.println("Erro: "+con.getResponseMessage());
        }
       
        return null;
    }
    
    // Emissão de Nota Fiscal Eletrônica com detalhamento específico de combustível
    public String emitirNotaFiscal_Combustivel() throws IOException, ParseException {
        /*
        * FileReader lê o arquivo emitirNotaFiscal_Combustivel.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/emitirNotaFiscal_Combustivel.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/emissao/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "POST");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
        
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
            
            // Finaliza conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("chave"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro retornado da API: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e.getMessage());
            System.out.println("Erro: "+con.getResponseMessage());
        }
       
        return null;
    }
    
    // Emissão de Nota Fiscal Eletrônica com detalhamento específico de medicamentos
    public String emitirNotaFiscal_Medicamentos() throws IOException, ParseException {
        /*
        * FileReader lê o arquivo emitirNotaFiscal_Medicamentos.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/emitirNotaFiscal_Medicamentos.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/emissao/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "POST");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
        
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
            
            // Finaliza conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("chave"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro retornado da API: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e.getMessage());
            System.out.println("Erro: "+con.getResponseMessage());
        }
       
        return null;
    }
    
    // Emissão de Nota Fiscal Eletrônica com detalhamento específico de rastreabilidade
    public String emitirNotaFiscal_Rastreabilidade() throws IOException, ParseException {
        /*
        * FileReader lê o arquivo emitirNotaFiscal_Rastreabilidade.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/emitirNotaFiscal_Rastreabilidade.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/emissao/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "POST");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
        
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
            
            // Finaliza conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("chave"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro retornado da API: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e.getMessage());
            System.out.println("Erro: "+con.getResponseMessage());
        }
       
        return null;
    }
    
    // Emissão de Nota Fiscal Eletrônica com detalhamento específico de rastreabilidade
    public String emitirNotaFiscal_VeiculosNovos() throws IOException, ParseException {
        /*
        * FileReader lê o arquivo emitirNotaFiscal_VeiculosNovos.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/emitirNotaFiscal_VeiculosNovos.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/emissao/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "POST");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
        
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
            
            // Finaliza conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("chave"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro retornado da API: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e.getMessage());
            System.out.println("Erro: "+con.getResponseMessage());
        }
       
        return null;
    }
    
    // Emissão de Nota Fiscal Eletrônica
    public String emitirNotaFiscal() throws IOException, ParseException {
        /*
        * FileReader lê o arquivo emitirNotaFiscal.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/emitirNotaFiscal.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/emissao/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "POST");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
        
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
            
            // Finaliza conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("chave"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro retornado da API: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e.getMessage());
            System.out.println("Erro: "+con.getResponseMessage());
        }
       
        return null;
    }
    
    /**
    * Informações da Nota Fiscal Eletrônica
    *
    * A Nota Fiscal de Ajuste é destinada somente para fins específicos de escrituração
    * contábil para empresas de Lucro Normal ou Presumido, não representando saída ou entrada
    * de produtos. Utilizado para nota de crédito de ICMS como transferência, ressarcimento
    * ou restituição do ICMS.
    */
    public String emitirNotaFiscalAjuste() throws IOException, ParseException {
        /* FileReader lê o arquivo emitirNotaFiscalAjuste.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/emitirNotaFiscalAjuste.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/ajuste/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "POST");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
     
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
           
            //Finaliza a conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("status"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e);
            System.out.println("Erro: "+ con.getResponseMessage());
        }
        
        return null;
    }
    
    /**
    * Informações da Nota Fiscal Complementar
    *
    * A Nota Fiscal Complementar é destinada para acrescentar dados e valores
    * não informados no documento fiscal original. Utilizado para acréscimo no preço
    * e quantidade da mercadoria ou somar valores faltantes dos impostos ICMS, ICMS-ST e IPI.
    *
    * OBS: Deve ser complementado o Preço/Quantidade OU Impostos.
    * OB2: Caso deseje complementar as duas opções devem ser emitidas NF-e separadas.
    */
    public String emitirNotaFiscalComplementar_Imposto() throws IOException, ParseException {
        /*
        * FileReader lê o arquivo emitirNotaFiscalComplementar_Imposto.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/emitirNotaFiscalComplementar_Imposto.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/complementar/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "POST");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
     
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
           
            //Finaliza a conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("status"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e);
            System.out.println("Erro: "+ con.getResponseMessage());
        }
        
        return null;
    }
    
    /**
    * Informações da Nota Fiscal Complementar
    *
    * A Nota Fiscal Complementar é destinada para acrescentar dados e valores
    * não informados no documento fiscal original. Utilizado para acréscimo no preço
    * e quantidade da mercadoria ou somar valores faltantes dos impostos ICMS, ICMS-ST e IPI.
    *
    * OBS: Deve ser complementado o Preço/Quantidade OU Impostos.
    * OB2: Caso deseje complementar as duas opções devem ser emitidas NF-e separadas.
    */
    public String emitirNotaFiscalComplementar_PrecoQuantidade() throws IOException, ParseException {
        /*
        * FileReader lê o arquivo emitirNotaFiscalComplementar_PrecoQuantidade.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/emitirNotaFiscalComplementar_PrecoQuantidade.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/complementar/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "POST");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
     
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
           
            //Finaliza a conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("status"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e);
            System.out.println("Erro: "+ con.getResponseMessage());
        }
        
        return null;
    }
    
    // Emissão de Nota Fiscal de Devolução
    public String emitirNotaFiscalDevolucao() throws IOException, ParseException {
        /*
        * FileReader lê o arquivo EmissaoNotaFiscalDevolucao.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/emitirNotaFiscalDevolucao.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/devolucao/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "POST");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
     
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
           
            //Finaliza a conexão
            con.disconnect();
            
            try{
                
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("log"));
                
                return json_input.toString();
                
            } catch(Exception e){
                System.out.println("Erro: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e);
            System.out.println("Erro: "+ con.getResponseMessage());
        }
        
        return null;
    }
    
    // Inutilizar numeração
    public String inutilizarNumeracao() throws IOException, ParseException {
        /*
        * FileReader lê o arquivo inutilizarNumeracao.json, caso os dados venha de um 
        * formulário ou banco de dados passar o objeto com os dados diretamente 
        * no .parse(object) e ignorar essa linha de código.
        */
        FileReader file_json = new FileReader("src/WebmaniaBR/ExemploJSON/inutilizarNumeracao.json");
        
        // Objeto com os dados para ser enviados a API.
        Object json_output = new JSONParser().parse(file_json);
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/inutilizar/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "PUT");
        
        // Permite a conexão atual a enviar e receber dados
        con.setDoOutput(true);
        con.setDoInput(true);
     
        try{
            
            // Envia os dados para API e fecha a conexão de saida
            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.write(json_output.toString().getBytes());
            output.flush();
            output.close();
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) this.trataRespostaApi(new InputStreamReader(con.getInputStream()));
           
            //Finaliza a conexão
            con.disconnect();
            
            try{
                // Exibi um atributo especifico do retorno da API
                System.out.println(json_input.get("log"));
                
                return json_input.toString();
            } catch(Exception e){
                System.out.println("Erro: "+ json_input.getString("error"));
            }

        }catch(IOException e){
            System.out.println("Erro: "+e);
            System.out.println("Erro: "+ con.getResponseMessage());
        }
        
        return null;
    }
    
    /**
    * Status do Sefaz
    *
    * OBS: A utilização do endpoint deve ser realizada como demonstrativo do Status do
    * Sefaz em sua plataforma, sendo necessário trabalhar com cache de ao menos 10 minutos.
    * Não é necessário realizar a requisição antes da emissão de cada Nota Fiscal,
    * porque este procedimento é realizado de forma automática em todos os endpoints.
    */
    public String statusSefaz() throws IOException{
        
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/sefaz/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "GET");
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        try {
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) trataRespostaApi(new InputStreamReader(con.getInputStream()));
            
            // Finaliza a conexão
            con.disconnect();
            
            try{
                // Exibi um atributo especifico do retorno da API
                //System.out.println(json_input.get("status"));
                
                return json_input.toString();
            } catch(Exception e){
                System.out.println("Erro retornado da API: "+ json_input.getString("error"));
            }
            
        } catch (IOException e) {
            System.out.println("Erro: "+e);
            System.out.println("Erro: "+ con.getResponseMessage());
        }
        
        return null;
    }
    
    // Validar dias restantes do Certificado A1
    public int validadeCertificadoA1() throws IOException {
        // Define a URL da API
        URL url = new URL("https://webmaniabr.com/api/1/nfe/certificado/");
        
        // Prepara a conexão
        HttpURLConnection con = this.preparaConexao(url, "GET");
        
        // Cria um objeto JSON para facilitar o tratamento dos dados de retorno da API
        JSONObject json_input = null;
        
        try {
            
            // Trata a resposta da API e retorna para um JSONObject
            json_input = (JSONObject) trataRespostaApi(new InputStreamReader(con.getInputStream()));
            
            // Finaliza a conexão
            con.disconnect();
            
            try{
                
                return Integer.parseInt(json_input.get("expiration").toString());
                
            } catch(Exception e){
                System.out.println("Erro retornado da API: "+ json_input.getString("error"));
            }
            
        } catch (IOException e) {
            System.out.println("Erro: "+e);
            System.out.println("Erro: "+ con.getResponseMessage());
        }
        
        return 0;
    }
    
}
