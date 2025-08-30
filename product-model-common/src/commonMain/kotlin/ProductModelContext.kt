import kotlinx.datetime.Instant
import models.*
import stubs.ProductModelStubs

data class ProductModelContext(
    var command: ProductModelCommand = ProductModelCommand.NONE,
    var state: ProductModelState = ProductModelState.NONE,
    var errors: MutableList<ProductModelError> = mutableListOf(),

    var workMode: ProductModelWorkMode = ProductModelWorkMode.TEST,
    var stubCase: ProductModelStubs = ProductModelStubs.NONE,

    var requestId: ProductModelRequestId = ProductModelRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var productModelRequest: ProductModel = ProductModel(),
    var productModelsRequest: MutableList<ProductModel> = mutableListOf(),
    var productModelFilterRequest: ProductModelFilter = ProductModelFilter(),
    var productModelResponse: ProductModel = ProductModel(),
    var productModelsResponse: MutableList<ProductModel> = mutableListOf(),
)