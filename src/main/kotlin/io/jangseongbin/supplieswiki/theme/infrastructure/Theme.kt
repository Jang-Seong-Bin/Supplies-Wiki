package io.jangseongbin.supplieswiki.theme.infrastructure

import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.GenerationType.AUTO
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

    @Enumerated(STRING)
    val category: Category,

    @CollectionTable
    @ElementCollection(fetch = EAGER)
    val contents: List<Content>,

    @CreatedDate
    val createdAt: LocalDateTime = now(),

    @LastModifiedDate
    val updatedAt: LocalDateTime? = null,
)
