package ru.otus.otuskotlin.player.s2

import kotlinx.cinterop.*
import ru.otus.otuskotlin.player.GstStructure
import ru.otus.otuskotlin.player.gintVar
import ru.otus.otuskotlin.player.gst_structure_get_int

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
