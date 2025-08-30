import com.crowdproj.product.model.api.v1.models.*
import exceptions.UnknownProductModelCommand
import models.*

fun ProductModelContext.toResponse(): IProductModelResponse = when (this.command) {
    ProductModelCommand.CREATE -> toCreateResponse()
    ProductModelCommand.READ -> toReadResponse()
    ProductModelCommand.UPDATE -> toUpdateResponse()
    ProductModelCommand.DELETE -> toDeleteResponse()
    ProductModelCommand.SEARCH -> toSearchResponse()
    ProductModelCommand.NONE -> throw UnknownProductModelCommand(this.command)
}

fun ProductModelContext.toCreateResponse() = ProductModelCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductModelState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toResponseErrors(),
    productModel = productModelResponse.toResponseObject()
)

fun ProductModelContext.toReadResponse() = ProductModelReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductModelState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toResponseErrors(),
    productModels = productModelsResponse.toResponseObject()
)

fun ProductModelContext.toUpdateResponse() = ProductModelUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductModelState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toResponseErrors(),
    productModel = productModelResponse.toResponseObject()
)

fun ProductModelContext.toDeleteResponse() = ProductModelDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductModelState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toResponseErrors(),
    productModel = productModelResponse.toResponseObject()
)

fun ProductModelContext.toSearchResponse() = ProductModelSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == ProductModelState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toResponseErrors(),
    productModels = productModelsResponse.toResponseObject()
)

private fun ProductModel.toResponseObject() = ProductModelResponseObject(
    id = id.takeIf { it != ProductModelId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    productGroupId = productGroupId.asString().ifBlank { "" },
    deleted = deleted,
    lock = lock.takeIf { it != ProductModelLock.NONE }?.asString(),
    ownerId = ownerId.takeIf { it != ProductModelUserId.NONE }?.asString(),
)

private fun List<ProductModelError>.toResponseErrors(): List<Error>? = this
    .map { it.toError() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ProductModelError.toError() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    title = description.takeIf { it.isNotBlank() },
    description = exception?.message
)

private fun List<ProductModel>.toResponseObject(): List<ProductModelResponseObject> = this.map { it.toResponseObject() }