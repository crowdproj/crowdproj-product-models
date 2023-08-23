import com.crowdproj.product.models.api.v1.models.*
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {

    private val request: IProductModelRequest = ProductModelCreateRequest(
        requestId = "111",
        debug = CpBaseDebug(
            mode = CpRequestDebugMode.STUB,
            stub = CpRequestDebugStubs.SUCCESS
        ),
        productModel = ProductModelCreateObject(
            name = "product model name",
            description = "product model description",
            productGroupId = "3"
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"requestId\":\\s*\"111\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"name\":\\s*\"product model name\""))
        assertContains(json, Regex("\"description\":\\s*\"product model description\""))
        assertContains(json, Regex("\"productGroupId\":\\s*\"3\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as ProductModelCreateRequest

        assertEquals(request, obj)
    }
}