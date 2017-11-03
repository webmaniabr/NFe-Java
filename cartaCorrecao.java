/*
JSON request:

{
  "chave":"45150819652219000198550990000000011442380343",
  "correcao":"O CFOP correto é 5.102 referente a revenda tributada no mesmo estado.",
  "ambiente":1
}
*/

OkHttpClient client = new OkHttpClient();

MediaType mediaType = MediaType.parse("application/json");
RequestBody body = RequestBody.create(mediaType, "{\"chave\": \"45150819652219000198550990000000011442380343\",\"correcao\": \"O CFOP correto é 5.102 referente a revenda tributada no mesmo estado.\",\"ambiente\": \"1\"}");

Request request = new Request.Builder()
  .url("https://webmaniabr.com/api/1/nfe/cartacorrecao/")
  .post(body)
  .addHeader("cache-control", "no-cache")
  .addHeader("content-type", "application/json")
  .addHeader("x-consumer-key", "SEU_CONSUMER_KEY")
  .addHeader("x-consumer-secret", "SEU_CONSUMER_SECRET")
  .addHeader("x-access-token", "SEU_ACCESS_TOKEN")
  .addHeader("x-access-token-secret", "SEU_ACCESS_TOKEN_SECRET")
  .build();

Response response = client.newCall(request).execute();
