package models

data class ProductModel(
    var id: ProductModelId = ProductModelId.NONE,
    var name: String = "",
    var description: String = "",
    var productGroupId: ProductGroupId = ProductGroupId.NONE,
    var deleted: Boolean = false,
    var lock: ProductModelLock = ProductModelLock.NONE,
    var ownerId: ProductModelUserId = ProductModelUserId.NONE,
)