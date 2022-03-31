package com.example.repositoriesfromthesearch.unitTests

import com.example.repositoriesfromthesearch.unitTests.stub.ScheduleProviderStub
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import model.SearchResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import presenter.search.SearchPresenter
import repository.GitHubRepository
import view.search.IViewSearchContract

class SearchPresenterTestRx {
    private lateinit var presenter: SearchPresenter

    @Mock
    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var viewContract: IViewSearchContract

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(repository, ScheduleProviderStub())
    }

    //Проверим вызов метода searchGitHub() у нашего Репозитория
    @Test
    fun searchGitHub_Test(){
        `when`(repository.searchGithub(SEARCH_QUERY)).thenReturn(
            Observable.just(
                SearchResponse(1, listOf())
            )
        )
        presenter.searchGitHub(SEARCH_QUERY)
        verify(repository, times(1)).searchGithub(SEARCH_QUERY)
    }



    companion object{
        private const val SEARCH_QUERY = "some query"
        private const val ERROR_TEXT = "error"
    }
}