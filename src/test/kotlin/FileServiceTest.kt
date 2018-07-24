import com.kart.classification.service.FileService
import com.kart.classification.service.RaceService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import java.io.IOException

class FileServiceTest{

    private lateinit var fileService: FileService
    private lateinit var raceService: RaceService

    @Before
    fun setup(){
        fileService = mock()
        raceService = mock()

    }

    @Test(expected = IOException::class)
    fun shouldReturnIOException() {
        val filename = "src/test/resources/test.txt"
        whenever(fileService.fileInitialize(filename)).thenReturn(null)
    }

}