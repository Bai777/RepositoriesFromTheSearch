package repository

import model.SearchResponse
import presenter.IRepositoryContract
import retrofit2.Response

internal class FakeGitHubRepository : IRepositoryContract {
    override fun searchGithub(query: String, callback: RepositoryCallback) {
        callback.handleGitHubResponse(Response.success(SearchResponse(42, listOf())))
    }
}