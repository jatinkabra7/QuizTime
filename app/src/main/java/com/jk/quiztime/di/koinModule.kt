package com.jk.quiztime.di

import com.jk.quiztime.data.local.DataStoreFactory
import com.jk.quiztime.data.local.DatabaseFactory
import com.jk.quiztime.data.local.QuizDB
import com.jk.quiztime.data.remote.HttpClientFactory
import com.jk.quiztime.data.remote.KtorRemoteQuizDataSource
import com.jk.quiztime.data.remote.QuizRemoteDataSource
import com.jk.quiztime.data.repository.IssueReportRepositoryImpl
import com.jk.quiztime.data.repository.QuizQuestionRepositoryImpl
import com.jk.quiztime.data.repository.QuizTopicRepositoryImpl
import com.jk.quiztime.data.repository.UserPreferencesRepositoryImpl
import com.jk.quiztime.domain.repository.IssueReportRepository
import com.jk.quiztime.domain.repository.QuizQuestionRepository
import com.jk.quiztime.domain.repository.QuizTopicRepository
import com.jk.quiztime.domain.repository.UserPreferencesRepository
import com.jk.quiztime.presentation.dashboard.DashboardViewModel
import com.jk.quiztime.presentation.issue_report.IssueReportViewModel
import com.jk.quiztime.presentation.quiz.QuizViewModel
import com.jk.quiztime.presentation.result.ResultViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule = module {

    single { HttpClientFactory.create() }

    single { DatabaseFactory.create(get()) }

    single { get<QuizDB>().quizTopicDao() }

    single { get<QuizDB>().questionDao() }

    single { get<QuizDB>().userAnswerDao() }

    single { DataStoreFactory.create(get()) }

    singleOf(::KtorRemoteQuizDataSource).bind<QuizRemoteDataSource>()

    singleOf(::QuizQuestionRepositoryImpl).bind<QuizQuestionRepository>()
    viewModelOf(::QuizViewModel)

    singleOf(::QuizTopicRepositoryImpl).bind<QuizTopicRepository>()
    viewModelOf(::DashboardViewModel)

    viewModelOf(::ResultViewModel)

    viewModelOf(::IssueReportViewModel)

    singleOf(::IssueReportRepositoryImpl).bind<IssueReportRepository>()

    singleOf(::UserPreferencesRepositoryImpl).bind<UserPreferencesRepository>()
}