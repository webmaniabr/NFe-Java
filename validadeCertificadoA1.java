OkHttpClient client = new OkHttpClient();

Request request = new Request.Builder()
  .url("https://webmaniabr.com/api/1/nfe/certificado/")
  .get()
  .addHeader("cache-control", "no-cache")
  .addHeader("content-type", "application/json")
  .addHeader("x-consumer-key", "SEU_CONSUMER_KEY")
  .addHeader("x-consumer-secret", "SEU_CONSUMER_SECRET")
  .addHeader("x-access-token", "SEU_ACCESS_TOKEN")
  .addHeader("x-access-token-secret", "SEU_ACCESS_TOKEN_SECRET")
  .build();

Response response = client.newCall(request).execute();
