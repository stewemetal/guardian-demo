package co.zsmb.rainbowcake.guardiandemo.data.network

import co.zsmb.rainbowcake.guardiandemo.BuildConfig
import co.zsmb.rainbowcake.guardiandemo.data.network.interceptor.AuthInterceptor
import co.zsmb.rainbowcake.guardiandemo.data.network.interceptor.FieldsInterceptor
import co.zsmb.rainbowcake.guardiandemo.data.network.interceptor.SearchQueryInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(FieldsInterceptor())
        .addInterceptor(SearchQueryInterceptor())
        .addInterceptor(AuthInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.GUARDIAN_API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideNytAPI(retrofit: Retrofit): GuardianApi = retrofit.create()

}
