package com.hqapps.storeviewer.di

import com.hqapps.storeviewer.api.StoreAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("domain")
    fun provideDomain(): String {
        return "rest.paysafecard.com"
    }

    @Provides
    @Singleton
    fun provideCertificatePinner(@Named("domain") domain: String): CertificatePinner =
        CertificatePinner.Builder()
            .add(domain,
                "sha256/4RqkrIrErrVo/xOhdRj/PH+oayfIHW0q+GDLMu1KtEg="
            )
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor, pinner: CertificatePinner): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .certificatePinner(pinner)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient, @Named("domain") domain: String): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://$domain")
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(httpClient)
            .build()

    @Provides
    @Singleton
    fun provideStoreAPI(retrofit: Retrofit): StoreAPI =
        retrofit.create(StoreAPI::class.java)
}