package util

import com.sample.shopping.simpleorderservice.controller.models.requests.ItemName
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemDetail
import com.sample.shopping.simpleorderservice.controller.models.requests.ItemsRequest

class OrderServiceTestUtil {
    companion object {
        fun getTestItemsRequest(
            itemDetails: List<ItemDetail> = listOf()
        ) = ItemsRequest(itemDetails)

        fun getTestItems(
            itemDetails: List<ItemDetail> = listOf()
        ) = ItemsRequest(itemDetails)

        fun getTestItemDetailRequest(
            name: ItemName = ItemName.APPLE,
            quantity: Int = 5
        ) = ItemDetail(
            name = name,
            quantity = quantity
        )

        // Ideally, this is different from the request object.
        // This would represent the model object.
        fun getTestItemDetail(
            name: ItemName = ItemName.APPLE,
            quantity: Int = 5
        ) = ItemDetail(
            name = name,
            quantity = quantity
        )
    }
}