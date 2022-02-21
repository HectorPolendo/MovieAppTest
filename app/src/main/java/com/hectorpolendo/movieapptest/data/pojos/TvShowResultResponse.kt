package com.hectorpolendo.movieapptest.data.pojos

data class TvShowResultResponse(
    val page: Int,
    val results: List<TvShowResponse>,
    val total_pages: Int,
    val total_results: Int
)