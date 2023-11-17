package io.jangseongbin.supplieswiki.theme.service

import io.jangseongbin.supplieswiki.Fixture
import io.jangseongbin.supplieswiki.theme.infrastructure.Category
import io.jangseongbin.supplieswiki.theme.infrastructure.ThemeRepository
import io.jangseongbin.supplieswiki.theme.request.CreateTheme
import io.jangseongbin.supplieswiki.theme.request.CreateThemeRequest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ThemeServiceTest(
    private val themeRepository: ThemeRepository,
) : BehaviorSpec() {
    private val themeService = ThemeService(themeRepository)

    init {
        given("인증된 사용자가 로그인된 상황에서") {
            val user = Fixture.user(updated = true)

            `when`("테마 정보 입력란에") {
                val request = CreateThemeRequest(
                    name = "swim",
                    userId = user.id,
                    contents = listOf("첫번째 콘텐츠", "일곱번째 콘텐츠", "백만스물두번째 콘텐츠"),
                    category = Category.SPORTS,
                )
                and("정보를 기입하고 테마 등록 버튼을 누르면") {
                    val input = CreateTheme(request)
                    themeService.createTheme(input)

                    then("새로운 테마가 생성된다.") {
                        val themes = themeRepository.findAll()
                        themes.size.shouldBe(1)

                        val theme = themes[0]
                        theme.name shouldBeEqual  "swim"
                        theme.category shouldBeEqual input.category
                    }
                }
                and("이미 존재하는 테마를 기입하고 등록 버튼을 누르면") {
                    val sameRequest = CreateThemeRequest(
                        name = "swim",
                        userId = user.id,
                        contents = listOf("전혀 다른 글", "새롭게 태어난 글", "따끈 따끈한 글"),
                        category = Category.SPORTS,
                    )

                    themeService.createTheme(CreateTheme(sameRequest))

                    then("버전 업 되어 중복으로 생성된다") {
                        val themes = themeRepository.findAllByName("swim")
                        themes.size.shouldBe(2)
                    }
                }
            }
        }
    }
}
