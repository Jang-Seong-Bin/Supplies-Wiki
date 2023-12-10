package io.jangseongbin.supplieswiki.theme.infrastructure

import io.jangseongbin.supplieswiki.theme.request.UpdateTheme
import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.FetchType.EAGER
import jakarta.persistence.GenerationType.AUTO
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.time.LocalDateTime.now

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
) {
    fun update(updateTheme: UpdateTheme): Theme {
        val contents = this.contents.toMutableList()
        contents.add(updateTheme.content)
        return Theme(
            id = this.id,
            name = this.name,
            category = this.category,
            contents = contents.toList(),
            createdAt = this.createdAt,
        )
    }
}
