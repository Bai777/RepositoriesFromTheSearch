package presenter.search

import presenter.IPresenterContract

internal interface IPresenterSearchContract: IPresenterContract {
    fun searchGitHub(searchQuery: String)
}