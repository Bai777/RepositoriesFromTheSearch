package presenter.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import model.SearchResponse
import presenter.IRepositoryContract
import presenter.search.scheduler.SchedulerProvider
import presenter.search.scheduler.SearchSchedulerProvider
import repository.GitHubApi
import repository.GitHubRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import view.search.MainActivity.Companion.BASE_URL

class SearchViewModel(
    private val repository: IRepositoryContract = GitHubRepository(
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GitHubApi::class.java)
    ),
    private val appSchedulerProvider: SchedulerProvider = SearchSchedulerProvider()
): ViewModel() {

    private val _liveData = MutableLiveData<ScreenState>()
    private val liveData: LiveData<ScreenState> = _liveData

    fun subscribeToLiveData() = liveData
    fun searchGitHub(searchQuery: String){
        //Dispose
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            repository.searchGithub(searchQuery)
                .subscribeOn(appSchedulerProvider.io())
                .observeOn(appSchedulerProvider.ui())
                .doOnSubscribe{_liveData.value = ScreenState.Loading}
                .subscribeWith(object: DisposableObserver<SearchResponse>()
                {
                    override fun onNext(t: SearchResponse) {
                        TODO("Not yet implemented")
                    }

                    override fun onError(e: Throwable) {
                        TODO("Not yet implemented")
                    }

                    override fun onComplete() {
                        TODO("Not yet implemented")
                    }

                }
                )
        )
    }
}

sealed class ScreenState{
    object Loading: ScreenState()
    data class Working(val searchResponse: SearchResponse): ScreenState()
    data class Error(val error: Throwable): ScreenState()
}