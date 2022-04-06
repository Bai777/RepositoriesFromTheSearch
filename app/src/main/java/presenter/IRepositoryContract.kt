package presenter

import io.reactivex.Observable
import model.SearchResponse
import repository.RepositoryCallback

interface IRepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )

    fun searchGithub(
        query: String,
    ): Observable<SearchResponse>

    suspend fun searchGithubAsync(
        query: String
    ): SearchResponse
}