package ru.alexsuvorov.employee_service.api;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static ReaderApi readerApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://gitlab.65apps.com") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        readerApi = retrofit.create(ReaderApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static ReaderApi getApi() {
        return readerApi;
    }
}