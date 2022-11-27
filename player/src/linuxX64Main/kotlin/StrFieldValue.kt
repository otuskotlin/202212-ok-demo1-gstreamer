package ru.otus.otuskotlin.player

import kotlinx.cinterop.*

inline fun <reified T> strFieldValue(structure: CPointer<GstStructure>?, field: String): T = memScoped {
    when (T::class) {
        Int::class -> {
            val num = alloc<gintVar>()
            gst_structure_get_int(structure, field, num.ptr)
            num.ptr[0] as T
        }

        else -> throw Exception("Wrong type ${T::class}")
    }
}
