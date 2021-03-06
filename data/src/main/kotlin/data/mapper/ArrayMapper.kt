package data.mapper

import com.google.firebase.database.DataSnapshot
import model.FirebaseObject

fun <T> DataSnapshot.getArrayValue(clazz: Class<T>): List<T> {
    val result = ArrayList<T>()

    val items = this.children.iterator()

    while (items.hasNext()) {
        val currentItem = items.next()

        val item = currentItem.getValue(clazz)

        item?.let {
            (item as FirebaseObject).objectKey = currentItem.key
            result.add(item)
        }
    }

    return result
}
