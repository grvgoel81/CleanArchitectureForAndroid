package com.mitteloupe.whoami.home.presentation.viewmodel

import com.mitteloupe.whoami.architecture.domain.UseCaseExecutor
import com.mitteloupe.whoami.architecture.presentation.BaseViewModel
import com.mitteloupe.whoami.home.domain.usecase.GetConnectionDetailsUseCase
import com.mitteloupe.whoami.home.domain.usecase.SaveConnectionDetailsUseCase
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionDetailsToDomainMapper
import com.mitteloupe.whoami.home.presentation.mapper.ConnectionStateToPresentationMapper
import com.mitteloupe.whoami.home.presentation.mapper.ExceptionToPresentationMapper
import com.mitteloupe.whoami.home.presentation.model.HomeNotification
import com.mitteloupe.whoami.home.presentation.model.HomeNotification.ConnectionSaved
import com.mitteloupe.whoami.home.presentation.model.HomeViewState
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Error
import com.mitteloupe.whoami.home.presentation.model.HomeViewState.Loading
import com.mitteloupe.whoami.home.presentation.navigation.ViewHistoryPresentationDestination

class HomeViewModel(
    private val getConnectionDetailsUseCase: GetConnectionDetailsUseCase,
    private val connectionStateToPresentationMapper: ConnectionStateToPresentationMapper,
    private val saveConnectionDetailsUseCase: SaveConnectionDetailsUseCase,
    private val connectionDetailsToDomainMapper: ConnectionDetailsToDomainMapper,
    private val exceptionToPresentationMapper: ExceptionToPresentationMapper,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel<HomeViewState, HomeNotification>(useCaseExecutor) {
    override val initialViewState = Loading

    fun onEnter() {
        updateViewState(Loading)
        getConnectionDetailsUseCase.run(
            onResult = { result ->
                updateViewState(
                    connectionStateToPresentationMapper.toPresentation(result)
                )
            },
            onException = { exception ->
                updateViewState(Error(exceptionToPresentationMapper.toPresentation(exception)))
            }
        )
    }

    fun onSaveDetails(connectionDetails: HomeViewState.Connected) {
        val domainConnectionDetails = connectionDetailsToDomainMapper.toDomain(connectionDetails)
        saveConnectionDetailsUseCase.run(
            value = domainConnectionDetails,
            onResult = {
                notify(ConnectionSaved(connectionDetails.ipAddress))
            },
            onException = { exception ->
                updateViewState(Error(exceptionToPresentationMapper.toPresentation(exception)))
            }
        )
    }

    fun onViewHistoryAction() {
        navigate(ViewHistoryPresentationDestination)
    }
}
