package dekauliya.fyp.mathqa.RestApi;

import dekauliya.fyp.mathqa.Models.User;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by dekauliya on 13/1/17.
 */

public interface LoginService {
    @POST("/api-auth")
    Call<User> basicLogin();
    
}
