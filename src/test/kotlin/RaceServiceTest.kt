import com.kart.classification.service.FileService
import com.kart.classification.service.RaceService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.time.format.DateTimeParseException

class RaceServiceTest{

    private lateinit var fileService: FileService
    private lateinit var raceService: RaceService

    @Before
    fun setup(){
        fileService = mock()
        raceService = mock()
    }

    @Test(expected = DateTimeParseException::class)
    fun shouldReturnDateTimeParseException() {
        val string = "aisdashdahdasdha"
        whenever(raceService.mountDomainByLine(string)).thenReturn(null)
    }
    
}