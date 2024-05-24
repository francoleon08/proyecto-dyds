package dyds.tvseriesinfo.model.apiConsummer;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class WikipediaAPIsConsummer {
    private WikipediaSearchAPI searchAPI;
    private WikipediaPageAPI pageAPI;

    private void configureAPIs() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        searchAPI = retrofit.create(WikipediaSearchAPI.class);
        pageAPI = retrofit.create(WikipediaPageAPI.class);
    }

}
