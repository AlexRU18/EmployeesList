package ru.alexsuvorov.employee_service.api;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static ReaderApi readerApi;
    private Retrofit retrofit;
    String BASE_URL = "http://gitlab.65apps.com/65gb/static/raw/master/";

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        readerApi = retrofit.create(ReaderApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static ReaderApi getApi() {
        return readerApi;
    }
}