package com.example.featuredsport.domain

import com.example.featuredsport.data.model.Sport

/**
 * Get random sport
 *
 */
interface GetRandomSport {
    /**
     * Invoke
     */
    suspend operator fun invoke(): Sport
}