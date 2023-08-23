import com.crowdproj.product.models.api.v1.models.*
import org.junit.Test
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
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"requestId\":\\s*\"122\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
        assertContains(json, Regex("\"name\":\\s*\"product model name\""))
        assertContains(json, Regex("\"description\":\\s*\"product model description\""))
        assertContains(json, Regex("\"productGroupId\":\\s*\"3\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as ProductModelCreateResponse

        assertEquals(response, obj)
    }
}