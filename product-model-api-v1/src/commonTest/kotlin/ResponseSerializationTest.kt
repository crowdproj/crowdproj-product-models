import com.crowdproj.product.model.api.v1.models.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {

    private val response: IProductModelResponse = ProductModelCreateResponse(
        requestId = "122",
        result = ResponseResult.SUCCESS,
        productModel = ProductModelResponseObject(
            id = "22",
            deleted = false,
            name = "product model name",
            description = "product model description",
            productGroupId = "3"
        )
    )

    @Test
    fun serialize() {
        val json = Json.encodeToString(IProductModelResponse.serializer(), response)

        assertContains(json, Regex("\"requestId\":\\s*\"122\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
        assertContains(json, Regex("\"name\":\\s*\"product model name\""))
        assertContains(json, Regex("\"description\":\\s*\"product model description\""))
        assertContains(json, Regex("\"productGroupId\":\\s*\"3\""))
    }

    @Test
    fun deserialize() {
        val json = Json.encodeToString(IProductModelResponse.serializer(), response)
        val obj = Json.decodeFromString(IProductModelResponse.serializer(), json) as ProductModelCreateResponse

        assertEquals(response, obj)
    }
}