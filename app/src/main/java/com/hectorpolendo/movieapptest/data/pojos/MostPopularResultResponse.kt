package com.hectorpolendo.movieapptest.data.pojos

data class MostPopularResultResponse(
    val page: Int,
    val results: List<MostPopularResponse>,
    val total_pages: Int,
    val total_results: Int
)