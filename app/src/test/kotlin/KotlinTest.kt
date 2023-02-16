import kotlin.test.Test
import kotlin.test.assertEquals

internal class KotlinTest {

    private val kotlinTest: KotlinApp = KotlinApp()

    @Test
    fun testGet() {
        request = kotlinTest.get()

        assertEquals(200, request.statusCode)
    }
}