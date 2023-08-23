import com.crowdproj.product.models.api.v1.models.*
import exceptions.UnknownProductModelCommand
import models.*

fun ProductModelContext.toTransportProductModel(): IProductModelResponse = when (val cmd = command) {
    ProductModelCommand.CREATE -> toTransportCreate()
    ProductModelCommand.READ -> toTransportRead()
    ProductModelCommand.UPDATE -> toTransportUpdate()
    ProductModelCommand.DELETE -> toTransportDelete()
    ProductModelCommand.SEARCH -> toTransportSearch()
    ProductModelCommand.NONE -> throw UnknownProductModelCommand(cmd)
}

fun ProductModelContext.toTransportCreate() = ProductModelCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductModelState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    productModel = productModelResponse.toTransport()
)

fun ProductModelContext.toTransportRead() = ProductModelReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductModelState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    productModels = productModelsResponse.toTransport()
)

fun ProductModelContext.toTransportUpdate() = ProductModelUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductModelState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    productModel = productModelResponse.toTransport()
)

fun ProductModelContext.toTransportDelete() = ProductModelDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductModelState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    productModel = productModelResponse.toTransport()
)

fun ProductModelContext.toTransportSearch() = ProductModelSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductModelState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    productModels = productModelsResponse.toTransport()
)

private fun ProductModel.toTransport() = ProductModelResponseObject(
    id = id.takeIf { it != ProductModelId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    productGroupId = productGroupId.asString().ifBlank { "" },
    deleted = deleted,
    lock = lock.takeIf { it != ProductModelLock.NONE }?.asString(),
    ownerId = ownerId.takeIf { it != ProductModelUserId.NONE }?.asString(),
)

private fun List<ProductModelError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ProductModelError.toTransport() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    title = description.takeIf { it.isNotBlank() },
    description = exception?.message
)

private fun List<ProductModel>.toTransport(): List<ProductModelResponseObject> = this.map { it.toTransport() }