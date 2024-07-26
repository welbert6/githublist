package com.mrrsoftware.githublist.di

import com.mrrsoftware.githublist.BuildConfig
import com.mrrsoftware.githublist.data.remote.GithubService
import com.mrrsoftware.githublist.data.repository.GitHubRepository
import com.mrrsoftware.githublist.data.repository.GitHubRepositoryImpl
import com.mrrsoftware.githublist.domain.usecase.RepositoriesUseCase
import com.mrrsoftware.githublist.presentation.viewmodel.RepositoriesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Welbert on 25/07/2024
 */



val appModule = module {
    single { provideRetrofit() }
    single { get<Retrofit>().create(GithubService::class.java) }
    single<GitHubRepository> { GitHubRepositoryImpl(get()) }
    factory { RepositoriesUseCase(get()) }
    viewModel { RepositoriesListViewModel(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}