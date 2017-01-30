package dekauliya.fyp.mathqa.RetrofitRestApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dekauliya on 29/1/17.
 */

public class MathQaRestService{
    public static final String BASE_URL =  "http://10.27.4.160:8001/apiv2/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel
            (HttpLoggingInterceptor.Level.BODY);

    private static Retrofit retrofit;
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }

        return retrofit.create(serviceClass);
    }
//
//    public static <S> S createService(Class<S> serviceClass, final AccessToken accessToken) {
//        if (httpClient == null) {
//            httpClient = new OkHttpClient.Builder();
//        }
//
//        if (accessToken != null) {
//            httpClient.addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request original = chain.request();
//
//                    Request.Builder requestBuilder = original.newBuilder()
//                            .header("Accept", "application/json")
//                            .header("Authorization", accessToken.getTokenType() + " " +
//                                    accessToken.getAccessToken())
//                            .method(original.method(), original.body());
//
//                    Request newRequest = requestBuilder.build();
//                    return chain.proceed(newRequest);
//                }
//            });
//        }
//        return retrofit.create(serviceClass);
//    }

}
