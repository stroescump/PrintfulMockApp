package com.stroescumarius.printfulmockapp.data.koin

import com.stroescumarius.printfulmockapp.data.repository.CharactersRepository
import com.stroescumarius.printfulmockapp.data.repository.CharactersRepositoryImpl
import com.stroescumarius.printfulmockapp.data.repository.FilmRepository
import com.stroescumarius.printfulmockapp.data.repository.FilmsRepositoryImpl
import com.stroescumarius.printfulmockapp.ui.characterDetails.CharacterDetailsViewModel
import com.stroescumarius.printfulmockapp.ui.mainActivity.MainViewModel
import com.stroescumarius.printfulmockapp.utils.retrofit.CharacterApi
import com.stroescumarius.printfulmockapp.utils.retrofit.FilmApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://swapi.dev/api/"
val appModules = module {

    viewModel { MainViewModel(get()) }

    viewModel { CharacterDetailsViewModel(get(), androidApplication()) }

    factory { FilmsRepositoryImpl(get()) }

    factory { provideCharacterApi(get()) }

    factory { provideFilmApi(get()) }

    factory { provideCharacterRepository(get()) }

    factory { provideFilmRepository(get()) }

    factory { provideRetrofitClient(get()) }

    factory { provideInterceptor() }

    single { provideRetrofit(get()) }
}

fun provideCharacterRepository(characterApi: CharacterApi): CharactersRepository {
    return CharactersRepositoryImpl(characterApi)
}

fun provideFilmRepository(filmApi: FilmApi): FilmRepository {
    return FilmsRepositoryImpl(filmApi)
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}

fun provideInterceptor(): Interceptor {
    return HttpLoggingInterceptor()
}

fun provideRetrofitClient(interceptor: Interceptor): OkHttpClient {
    return OkHttpClient().newBuilder().addInterceptor(interceptor).build()
}

fun provideCharacterApi(retrofit: Retrofit): CharacterApi {
    return retrofit.create(CharacterApi::class.java)
}

fun provideFilmApi(retrofit: Retrofit): FilmApi {
    return retrofit.create(FilmApi::class.java)
}
