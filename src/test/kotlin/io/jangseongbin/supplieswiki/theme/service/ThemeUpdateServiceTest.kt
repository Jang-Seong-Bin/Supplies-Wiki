package io.jangseongbin.supplieswiki.theme.service

import io.jangseongbin.supplieswiki.Fixture
import io.jangseongbin.supplieswiki.Fixture.updateRequestForNotExistingTheme
import io.jangseongbin.supplieswiki.theme.infrastructure.Category
import io.jangseongbin.supplieswiki.theme.infrastructure.ThemeRepository
import io.jangseongbin.supplieswiki.theme.request.CreateTheme
import io.jangseongbin.supplieswiki.theme.request.CreateThemeRequest
import io.jangseongbin.supplieswiki.theme.request.UpdateThemeRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ThemeUpdateServiceTest(
    private val themeRepository: ThemeRepository,

    private val themeService: ThemeService,
) : BehaviorSpec({
    given("테마가 없을 때") {
        `when`("테마를 수정하려고 하면") {
            then("NoSuchElementException이 발생한다.") {
                shouldThrow<NoSuchElementException> {
                    val invalidRequestUpdateTheme = updateRequestForNotExistingTheme()

                    themeService.updateTheme(invalidRequestUpdateTheme)
                }
            }
        }
    }

    given("테마가 정상적으로 존재할 때") {
        val user = Fixture.user(updated = true)

        val request = CreateThemeRequest(
            name = "swim",
            userId = user.id,
            contents = listOf("첫번째 콘텐츠", "일곱번째 콘텐츠", "백만스물두번째 콘텐츠"),
            category = Category.SPORTS,
        )
        val input = CreateTheme(request)
        val createdTheme = themeService.createTheme(input)

        `when`("테마를 업데이트 하면") {
            val updateTheme = UpdateThemeRequest(
                themeId = createdTheme.id,
                userId = user.id,
                contents = "백만스물세번째 컨텐츠",
            ).toUpdateTheme()
            themeService.updateTheme(updateTheme)
            then("컨텐츠 리스트가 추가된다.") {
                val theme = themeRepository.findById(createdTheme.id).get()

                theme.contents.size shouldBe 4
                theme.contents.last().contents shouldBe "백만스물세번째 컨텐츠"
            }
        }
    }
})
