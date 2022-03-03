package presenter.search

import model.SearchResponse
import repository.GitHubRepository
import retrofit2.Response
import view.search.IViewSearchContract
import repository.GitHubRepository.GitHubRepositoryCallback

internal class SearchPresenter internal constructor(
    private val viewContract: IViewSearchContract,
    private val repository: GitHubRepository
): IPresenterSearchContract, GitHubRepositoryCallback {

    override fun searchGitHub(searchQuery: String) {
    viewContract.displayLoading(true)
    repository.searchGithub(searchQuery, this)
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewContract.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract.displayError("Search results or total count are null")
            }
        } else {
            viewContract.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewContract.displayLoading(false)
        viewContract.displayError()
    }
}