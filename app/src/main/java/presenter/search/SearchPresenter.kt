package presenter.search

import model.SearchResponse
import presenter.IRepositoryContract
import repository.RepositoryCallback
import retrofit2.Response
import view.search.IViewSearchContract

internal class SearchPresenter internal constructor(
    private val repository: IRepositoryContract
): IPresenterSearchContract, RepositoryCallback {

    private var viewContract : IViewSearchContract? = null
    private var isAttached = false

    override fun searchGitHub(searchQuery: String) {
    viewContract?.displayLoading(true)
    repository.searchGithub(searchQuery, this)
    }

    override fun onAttach(view: IViewSearchContract) {
        viewContract = view
        isAttached = true
    }

    override fun onDetach() {
        viewContract = null
        isAttached = false
    }

    override fun isPresenterAttached(): Boolean {
        return isAttached
    }


    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract?.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewContract?.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract?.displayError("Search results or total count are null")
            }
        } else {
            viewContract?.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewContract?.displayLoading(false)
        viewContract?.displayError()
    }
}