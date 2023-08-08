package com.example.featuredsport.repository

import com.example.featuredsport.data.model.Sport

/**
 * Content repository
 */
interface ContentRepository {

    /**
     * Get featured sports
     *
     */
    suspend fun getFeaturedSports(): List<Sport>
}