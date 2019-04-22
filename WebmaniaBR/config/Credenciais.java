/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebmaniaBR.config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author equipe
 */
public class Credenciais {
    private String X_CONSUMER_KEY;
    private String X_CONSUMER_SECRET;
    private String X_ACCESS_TOKEN;
    private String X_ACCESS_TOKEN_SECRET;

    public Credenciais() {
        this.X_CONSUMER_KEY = "SEU_CONSUMER_KEY";
        this.X_CONSUMER_SECRET = "SEU_CONSUMER_SECRET";
        this.X_ACCESS_TOKEN = "SEU_ACCESS_TOKEN";
        this.X_ACCESS_TOKEN_SECRET = "SEU_ACCESS_TOKEN_SECRET";
    }

    public String getX_CONSUMER_KEY() {
        return X_CONSUMER_KEY;
    }

    public void setX_CONSUMER_KEY(String X_CONSUMER_KEY) {
        this.X_CONSUMER_KEY = X_CONSUMER_KEY;
    }

    public String getX_CONSUMER_SECRET() {
        return X_CONSUMER_SECRET;
    }

    public void setX_CONSUMER_SECRET(String X_CONSUMER_SECRET) {
        this.X_CONSUMER_SECRET = X_CONSUMER_SECRET;
    }

    public String getX_ACCESS_TOKEN() {
        return X_ACCESS_TOKEN;
    }

    public void setX_ACCESS_TOKEN(String X_ACCESS_TOKEN) {
        this.X_ACCESS_TOKEN = X_ACCESS_TOKEN;
    }

    public String getX_ACCESS_TOKEN_SECRET() {
        return X_ACCESS_TOKEN_SECRET;
    }

    public void setX_ACCESS_TOKEN_SECRET(String X_ACCESS_TOKEN_SECRET) {
        this.X_ACCESS_TOKEN_SECRET = X_ACCESS_TOKEN_SECRET;
    }
    
    // Método responsável por preparar a conexão com a API
    public HttpURLConnection preparaConexao(URL url, String method) throws IOException {
        
        try{
       
            // Abre a conexão
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //Declara método de envio
            con.setRequestMethod(method);

            //Adiciona os Headers
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("X-Consumer-Key", this.getX_CONSUMER_KEY());
            con.setRequestProperty("X-Consumer-Secret", this.getX_CONSUMER_SECRET());
            con.setRequestProperty("X-Access-Token", this.getX_ACCESS_TOKEN());
            con.setRequestProperty("X-Access-Token-Secret", this.getX_ACCESS_TOKEN_SECRET());

            // Retorna a conexão pronta
            return con;
            
        } catch ( IOException e ) {
            System.out.println("Erro: "+e.getMessage());
        }
        
        return null;
    }
    
    
}
