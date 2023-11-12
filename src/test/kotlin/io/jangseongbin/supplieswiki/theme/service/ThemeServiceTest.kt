package io.jangseongbin.supplieswiki.theme.service

import io.jangseongbin.supplieswiki.Fixture
import io.jangseongbin.supplieswiki.theme.infrastructure.ThemeRepository
import io.jangseongbin.supplieswiki.theme.request.CreateTheme
import io.jangseongbin.supplieswiki.theme.request.CreateThemeRequest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ThemeServiceTest(
    private val themeRepository: ThemeRepository,
) : BehaviorSpec() {
    private val themeService = ThemeService(repository = themeRepository)

    init {
        given("인증된 사용자가 로그인된 상황에서") {
            val user = Fixture.user(updated = true)

            `when`("테마 정보 입력란에") {
                val request = CreateThemeRequest(
                    themeName = "swim",
                    userId = user.id,
                    contents = listOf(Fixture.content(userId = user.id)),
                    category = listOf(Fixture.category()),
                )
                and("정보를 기입하고 테마 등록 버튼을 누르면") {
                    themeService.createTheme(CreateTheme(request))

                    then("새로운 테마가 생성된다.") {
                        val themes = themeRepository.findAll()
                        themes.size.shouldBe(1)

                        val theme = themes[0]
                        theme.name.shouldBe(request.themeName)
                        theme.categories.shouldBe(request.category)
                        theme.contents.shouldBe(request.contents)
                    }
                }
                and("이미 존재하는 테마를 기입하고 등록 버튼을 누르면") {
                    themeService.createTheme(request)

                    val sameRequest = CreateThemeRequest(
                        themeName = "swim",
                        userId = user.id,
                        contents = listOf(Fixture.content(userId = user.id)),
                        category = listOf(Fixture.category()),
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
