/*
JSON request:

{
  "sequencia": "101-109",
  "motivo": "Inutilização por problemas técnicos.",
  "ambiente": "1",
  "serie": "99",
  "modelo": "1"
}
*/

OkHttpClient client = new OkHttpClient();

MediaType mediaType = MediaType.parse("application/json");
RequestBody body = RequestBody.create(mediaType, "{\"sequencia\":\"101-109\",\"motivo\":\"Inutilização por problemas técnicos.\",\"ambiente\":\"1\",\"serie\":\"99\",\"modelo\":\"1\"}");

Request request = new Request.Builder()
  .url("https://webmaniabr.com/api/1/nfe/inutilizar/")
  .put(body)
  .addHeader("cache-control", "no-cache")
  .addHeader("content-type", "application/json")
  .addHeader("x-consumer-key", "SEU_CONSUMER_KEY")
  .addHeader("x-consumer-secret", "SEU_CONSUMER_SECRET")
  .addHeader("x-access-token", "SEU_ACCESS_TOKEN")
  .addHeader("x-access-token-secret", "SEU_ACCESS_TOKEN_SECRET")
  .build();

Response response = client.newCall(request).execute();
