package com.example.featuredsport.domain

import com.example.featuredsport.data.model.Sport
import com.example.featuredsport.repository.ContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation for [GetRandomSport]
 *
 */
class DefaultGetRandomSport @Inject constructor(
    private val contentRepository: ContentRepository
) : GetRandomSport {
    override suspend fun invoke(): Sport {
        val sports = contentRepository.getFeaturedSports()
        val randomIndex = Random.nextInt(sports.size - 1)
        return sports[randomIndex]
    }
}