package presenter

import repository.RepositoryCallback

internal interface IRepositoryContract {
    fun searchGitHub(
        query: String,
        callback: RepositoryCallback
    )
}