package presenter

import io.reactivex.Observable
import model.SearchResponse
import repository.RepositoryCallback

internal interface IRepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )

    fun searchGithub(
        query: String,
    ): Observable<SearchResponse>
}