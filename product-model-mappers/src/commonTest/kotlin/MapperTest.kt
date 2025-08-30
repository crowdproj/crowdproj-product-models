import com.crowdproj.product.model.api.v1.models.*
import models.*
import stubs.ProductModelStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val request = ProductModelCreateRequest(
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

        val context = request.toContext()

        assertEquals(ProductModelStubs.SUCCESS, context.stubCase)
        assertEquals(ProductModelWorkMode.STUB, context.workMode)
        assertEquals(ProductModelCommand.CREATE, context.command)
        assertEquals("product model name", context.productModelRequest.name)
        assertEquals("product model description", context.productModelRequest.description)
        assertEquals(ProductGroupId("3"), context.productModelRequest.productGroupId)
    }

    @Test
    fun toTransport() {
        val context = ProductModelContext(
            requestId = ProductModelRequestId("112"),
            command = ProductModelCommand.CREATE,
            productModelResponse = ProductModel(
                name = "product model name",
                description = "product model description",
                productGroupId = ProductGroupId("3")
            ),
            errors = mutableListOf(
                ProductModelError(
                    code = "err",
                    group = "request",
                    field = "title",
                    description = "wrong title",
                    exception = Exception("test exception")
                )
            ),
            state = ProductModelState.RUNNING,
        )

        val request = context.toResponse() as ProductModelCreateResponse

        assertEquals("112", request.requestId)
        assertEquals("product model name", request.productModel?.name)
        assertEquals("product model description", request.productModel?.description)
        assertEquals("3", request.productModel?.productGroupId)

        assertEquals(1, request.errors?.size)
        request.errors?.firstOrNull()?.let {
            assertEquals("err", it.code)
            assertEquals("request", it.group)
            assertEquals("title", it.field)
            assertEquals("wrong title", it.title)
            assertEquals("test exception", it.description)
        }

    }
}