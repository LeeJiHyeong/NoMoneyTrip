package com.mtjin.nomoneytrip.module

import com.mtjin.nomoneytrip.data.email_login.source.EmailLoginRepository
import com.mtjin.nomoneytrip.data.email_login.source.EmailLoginRepositoryImpl
import com.mtjin.nomoneytrip.data.home.source.HomeRepository
import com.mtjin.nomoneytrip.data.home.source.HomeRepositoryImpl
import com.mtjin.nomoneytrip.data.local_page.source.LocalPageRepository
import com.mtjin.nomoneytrip.data.local_page.source.LocalPageRepositoryImpl
import com.mtjin.nomoneytrip.data.login.source.LoginRepository
import com.mtjin.nomoneytrip.data.login.source.LoginRepositoryImpl
import com.mtjin.nomoneytrip.data.tour_write.source.TourWriteRepository
import com.mtjin.nomoneytrip.data.tour_write.source.TourWriteRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<LocalPageRepository> { LocalPageRepositoryImpl(get(named("tour")), get()) }
    single<EmailLoginRepository> { EmailLoginRepositoryImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<TourWriteRepository> { TourWriteRepositoryImpl(get()) }
}