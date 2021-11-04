package id.moviec.data.mapper

import io.reactivex.functions.Function
import java.util.*

abstract class Mapper<FROM, TO> : Function<FROM?, TO?> {
    @Throws(Exception::class)
    fun apply(fromList: Collection<FROM>): Collection<TO?> {
        val result: MutableCollection<TO?> = ArrayList()
        for (from in fromList) {
            val item = apply(from)
            result.add(item)
        }
        return result
    }

    @Throws(Exception::class)
    override fun apply(from: FROM): TO? {
        return null
    }
}