package com.shahrukhamd.githubuser.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shahrukhamd.githubuser.data.api.GithubService
import com.shahrukhamd.githubuser.data.model.GithubUser
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val service: GithubService,
    private val query: String
) : PagingSource<Int, GithubUser>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getPaginatedUser(query, position)
            val nextPage = if (response.body()?.items?.isEmpty() == true) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = response.body()?.items.orEmpty(),
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextPage
            )
        } catch (ioException: IOException) {
            return LoadResult.Error(ioException)
        } catch (httpException: HttpException) {
            return LoadResult.Error(httpException)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}