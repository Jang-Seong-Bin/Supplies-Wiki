package io.jangseongbin.supplieswiki.theme.domain

import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.AUTO
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

@Entity
class Theme(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Long = 0,

    val name: String,

    @CollectionTable
    @ElementCollection(fetch = EAGER)
    val categories: List<Category>,

    @CollectionTable
    @ElementCollection(fetch = EAGER)
    val contents: List<Content>,

    @CreatedDate
    val createdAt: LocalDateTime = now(),

    @LastModifiedDate
    val updatedAt: LocalDateTime? = null,
)
