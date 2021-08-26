package com.shahrukhamd.githubuser.utils

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val QUERY_DELAY_MILLS = 600L
private const val QUERY_CHAR_THRESHOLD = 3

/**
 * A simple class extending from [SearchView.OnQueryTextListener]
 * This will emit the query only after a predefined time delay and after threshold is reached
 *
 * @param lifecycle : Lifecycle of the view owner
 * @param charThresholds: Minimum number of chars that should be typed before listener is fired
 * @param onDebouncingQueryTextChange: Listener method which will get the query text
 */
class DebouncingQueryTextListener(
    lifecycle: Lifecycle,
    private val charThresholds: Int = QUERY_CHAR_THRESHOLD,
    private val onDebouncingQueryTextChange: (String) -> Unit
) : SearchView.OnQueryTextListener {
    var debouncePeriod: Long = QUERY_DELAY_MILLS

    private val coroutineScope = lifecycle.coroutineScope

    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        if (newText != null && newText.length >= charThresholds) {
            searchJob = coroutineScope.launch {
                newText.let {
                    delay(debouncePeriod)
                    onDebouncingQueryTextChange(newText)
                }
            }
        }
        return false
    }
}