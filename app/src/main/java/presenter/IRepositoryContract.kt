package presenter

import repository.RepositoryCallback

internal interface IRepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}