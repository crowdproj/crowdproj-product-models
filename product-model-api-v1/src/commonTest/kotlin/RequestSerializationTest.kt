import com.crowdproj.product.model.api.v1.models.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {

    private val request: IProductModelRequest = ProductModelCreateRequest(
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
        val json = Json.encodeToString(IProductModelRequest.serializer(), request)

        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"name\":\\s*\"product model name\""))
        assertContains(json, Regex("\"description\":\\s*\"product model description\""))
        assertContains(json, Regex("\"productGroupId\":\\s*\"3\""))
    }

    @Test
    fun deserialize() {
        val json = Json.encodeToString(IProductModelRequest.serializer(), request)
        val obj = Json.decodeFromString(IProductModelRequest.serializer(), json) as ProductModelCreateRequest

        assertEquals(request, obj)
    }
}