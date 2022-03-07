package presenter.search

import presenter.IPresenterContract
import view.search.IViewSearchContract

internal interface IPresenterSearchContract: IPresenterContract {
    fun searchGitHub(searchQuery: String)
    fun onAttach(view : IViewSearchContract)
    fun onDetach()
    fun isPresenterAttached() : Boolean
}