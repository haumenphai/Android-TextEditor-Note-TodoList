package promax.dohaumen.text_edittor_mvvm

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        print("".split(" ").size)
    }

    @Test
    fun testDate() {
        val date = Date()
        val simpleDateFormat = SimpleDateFormat("[E] dd-MM-yyyy k:mm:ss a")
        val dateResult = simpleDateFormat.format(date)
        println(dateResult)
    }
}