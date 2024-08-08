package uz.coder.mytaxi.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.coder.mytaxi.data.TaxiRepositoryImpl
import uz.coder.mytaxi.data.db.TaxiDatabase
import uz.coder.mytaxi.domain.useCase.AddTaxiUseCase
import uz.coder.mytaxi.domain.useCase.GetLastTaxiUseCase
import uz.coder.mytaxi.presentation.viewModel.TaxiViewModel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Singleton
    @Provides
    fun providesTaxiRepository(db:TaxiDatabase) = TaxiRepositoryImpl(db)

    @Singleton
    @Provides
    fun providesAddTaxiUseCase(repository: TaxiRepositoryImpl) = AddTaxiUseCase(repository)

    @Singleton
    @Provides
    fun providesGetLastUseCase(repository: TaxiRepositoryImpl) = GetLastTaxiUseCase(repository)

    @Singleton
    @Provides
    fun providesTaxiViewModel(getLastTaxiUseCase: GetLastTaxiUseCase) = TaxiViewModel(getLastTaxiUseCase)

    @Singleton
    @Provides
    fun providesTaxiDatabase(@ApplicationContext context: Context) = TaxiDatabase.getInstance(context)
}