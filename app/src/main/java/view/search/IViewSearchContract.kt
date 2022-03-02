package view.search

import model.SearchResult
import view.IViewContract

interface IViewSearchContract: IViewContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}