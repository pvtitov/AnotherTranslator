package pvtitov.anothertranslator.networking;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Павел on 24.12.2017.
 */

public class RetrofitFactory {

    public interface WebService {
        @GET("/api/v1.5/tr.json/translate")
        Call<TranslationModel> getTranslation (@Query("key") String key,
                                               @Query("lang") String lang,
                                               @Query("text") String text);
    }

    private static Retrofit retrofit = null;

    public static Retrofit getInstance(String baseUrl){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
