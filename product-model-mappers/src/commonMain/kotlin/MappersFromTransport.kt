import com.crowdproj.product.model.api.v1.models.*
import models.*
import stubs.ProductModelStubs

fun ProductModelContext.fromTransport(request: IRequest) = when (request) {
    is ProductModelCreateRequest -> fromTransport(request)
    is ProductModelReadRequest -> fromTransport(request)
    is ProductModelUpdateRequest -> fromTransport(request)
    is ProductModelDeleteRequest -> fromTransport(request)
    is ProductModelSearchRequest -> fromTransport(request)
}

fun ProductModelContext.fromTransport(request: ProductModelCreateRequest) {
    command = ProductModelCommand.CREATE
    productModelRequest = request.productModel?.toInternal() ?: ProductModel()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProductModelContext.fromTransport(request: ProductModelReadRequest) {
    command = ProductModelCommand.READ
    productModelsRequest = request.productModelIds.fromTransport()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProductModelContext.fromTransport(request: ProductModelUpdateRequest) {
    command = ProductModelCommand.UPDATE
    productModelRequest = request.productModel?.toInternal() ?: ProductModel()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProductModelContext.fromTransport(request: ProductModelDeleteRequest) {
    command = ProductModelCommand.DELETE
    productModelRequest = request.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProductModelContext.fromTransport(request: ProductModelSearchRequest) {
    command = ProductModelCommand.SEARCH
    productModelFilterRequest = request.productModelFilter?.toInternal() ?: ProductModelFilter()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun ProductModelCreateObject.toInternal() = ProductModel(
    name = this.name.orEmpty(),
    description = this.description.orEmpty(),
    productGroupId = ProductGroupId(this.productGroupId.orEmpty()),
)

private fun ProductModelUpdateObject.toInternal() = ProductModel(
    id = ProductModelId(this.id.orEmpty()),
    name = this.name.orEmpty(),
    description = this.description.orEmpty(),
    productGroupId = ProductGroupId(this.productGroupId.orEmpty()),
    lock = this.lock.toProductModelLock()
)

private fun ProductModelDeleteRequest.toInternal() = ProductModel(
    id = this.productModelId.orEmpty().toProductModelId(),
    lock = this.lock.toProductModelLock()
)

private fun ProductModelSearchFilter.toInternal() = ProductModelFilter(
    name = this.name.orEmpty(),
    description = this.description.orEmpty()
)

private fun String?.toProductModelLock() = this?.let { ProductModelLock(it) } ?: ProductModelLock.NONE

private fun List<String>?.fromTransport(): MutableList<ProductModel> =
    this?.let { productPropertyIds -> productPropertyIds.map { it.toProductModelWithId() } }?.toMutableList()
        ?: mutableListOf()

private fun String?.toProductModelId() = this?.let { ProductModelId(it) } ?: ProductModelId.NONE

private fun String?.toProductModelWithId() = ProductModel(id = this.toProductModelId())

private fun CpBaseDebug?.transportToStubCase(): ProductModelStubs = when (this?.stub) {
    CpRequestDebugStubs.SUCCESS -> ProductModelStubs.SUCCESS
    CpRequestDebugStubs.NOT_FOUND -> ProductModelStubs.NOT_FOUND
    CpRequestDebugStubs.BAD_ID -> ProductModelStubs.BAD_ID
    null -> ProductModelStubs.NONE
}

private fun CpBaseDebug?.transportToWorkMode(): ProductModelWorkMode = when(this?.mode) {
    CpRequestDebugMode.PROD -> ProductModelWorkMode.PROD
    CpRequestDebugMode.TEST -> ProductModelWorkMode.TEST
    CpRequestDebugMode.STUB -> ProductModelWorkMode.STUB
    null -> ProductModelWorkMode.PROD
}