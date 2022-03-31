package presenter.search

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import model.SearchResponse
import presenter.IRepositoryContract
import presenter.search.scheduler.SchedulerProvider
import presenter.search.scheduler.SearchSchedulerProvider
import repository.RepositoryCallback
import retrofit2.Response
import view.search.IViewSearchContract

internal class SearchPresenter internal constructor(
    private val repository: IRepositoryContract,
    private val appSchedulerProvider: SchedulerProvider = SearchSchedulerProvider()
) : IPresenterSearchContract, RepositoryCallback {

    private var viewContract: IViewSearchContract? = null
    private var isAttached = false

    override fun searchGitHub(searchQuery: String) {
        //Dispose
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            repository.searchGithub(searchQuery)
                .subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe { viewContract?.displayLoading(true) }
                .doOnTerminate { viewContract?.displayLoading(false) }
                .subscribeWith(object : DisposableObserver<SearchResponse>() {

                    override fun onNext(searchResponse: SearchResponse) {
                        val searchResult = searchResponse.searchResults
                        val totalCount = searchResponse.totalCount
                        if (searchResult != null && totalCount != null) {
                            viewContract?.displaySearchResults(
                                searchResult,
                                totalCount
                            )
                        } else {
                            viewContract?.displayError("Search results or total count are null")
                        }
                    }

                    override fun onError(e: Throwable) {
                        viewContract?.displayError(e.message ?: "Response is null or unsuccessful")
                    }

                    override fun onComplete() {

                    }
                })
        )
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