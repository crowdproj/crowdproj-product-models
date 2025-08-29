import com.crowdproj.product.model.api.v1.models.*
import models.*
import stubs.ProductModelStubs

fun ProductModelCreateRequest.toContext() = ProductModelContext(
    command = ProductModelCommand.CREATE,
    productModelRequest = this.productModel?.toProductModel() ?: ProductModel(),
    workMode = this.debug.toWorkMode(),
    stubCase = this.debug.toStubCase()
)

fun ProductModelReadRequest.toContext() = ProductModelContext(
    command = ProductModelCommand.READ,
    productModelsRequest = this.productModelIds?.map { it.toIdOnlyProductModel() }?.toMutableList() ?: mutableListOf(),
    workMode = this.debug.toWorkMode(),
    stubCase = this.debug.toStubCase()
)

private fun String?.toIdOnlyProductModel() = ProductModel(id = this.toProductModelId())

fun ProductModelUpdateRequest.toContext() = ProductModelContext(
    command = ProductModelCommand.UPDATE,
    productModelRequest = this.productModel?.toProductModel() ?: ProductModel(),
    workMode = this.debug.toWorkMode(),
    stubCase = this.debug.toStubCase()
)

fun ProductModelDeleteRequest.toContext() = ProductModelContext(
    command = ProductModelCommand.DELETE,
    productModelRequest = this.toProductModel(),
    workMode = this.debug.toWorkMode(),
    stubCase = this.debug.toStubCase()
)

fun ProductModelSearchRequest.toContext() = ProductModelContext(
    command = ProductModelCommand.SEARCH,
    productModelFilterRequest = this.productModelFilter?.toProductModel() ?: ProductModelFilter(),
    workMode = this.debug.toWorkMode(),
    stubCase = this.debug.toStubCase()
)

private fun ProductModelCreateObject.toProductModel() = ProductModel(
    name = this.name.orEmpty(),
    description = this.description.orEmpty(),
    productGroupId = ProductGroupId(this.productGroupId.orEmpty()),
)

private fun ProductModelUpdateObject.toProductModel() = ProductModel(
    id = ProductModelId(this.id.orEmpty()),
    name = this.name.orEmpty(),
    description = this.description.orEmpty(),
    productGroupId = ProductGroupId(this.productGroupId.orEmpty()),
    lock = this.lock.toProductModelLock()
)

private fun ProductModelDeleteRequest.toProductModel() = ProductModel(
    id = this.productModelId.toProductModelId(),
    lock = this.lock.toProductModelLock()
)

private fun ProductModelSearchFilter.toProductModel() = ProductModelFilter(
    name = this.name.orEmpty(),
    description = this.description.orEmpty()
)

private fun String?.toProductModelLock() = this?.let { ProductModelLock(it) } ?: ProductModelLock.NONE

private fun String?.toProductModelId() = this?.let { ProductModelId(it) } ?: ProductModelId.NONE

private fun CpBaseDebug?.toStubCase(): ProductModelStubs = when (this?.stub) {
    CpRequestDebugStubs.SUCCESS -> ProductModelStubs.SUCCESS
    CpRequestDebugStubs.NOT_FOUND -> ProductModelStubs.NOT_FOUND
    CpRequestDebugStubs.BAD_ID -> ProductModelStubs.BAD_ID
    null -> ProductModelStubs.NONE
}

private fun CpBaseDebug?.toWorkMode(): ProductModelWorkMode = when (this?.mode) {
    CpRequestDebugMode.PROD -> ProductModelWorkMode.PROD
    CpRequestDebugMode.TEST -> ProductModelWorkMode.TEST
    CpRequestDebugMode.STUB -> ProductModelWorkMode.STUB
    null -> ProductModelWorkMode.PROD
}