package dekauliya.fyp.mathqa.RetrofitRestApi;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by dekauliya on 29/1/17.
 */

public class TokenAuthenticator implements Authenticator{

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        if (response.request().header("Authorization") != null ||
                response.request().header("Proxy-Authorization") != null) {
            return null; // Give up, we've already failed to authenticate.
        }
        String credential = Credentials.basic("admin", "password");
        return response.request().newBuilder().header("Authorization", credential).build();
    }
}
