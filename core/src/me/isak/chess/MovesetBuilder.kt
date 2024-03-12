import kotlin.text.Regex
import friends

class Mb {
    var d: Array<String> = arrayOf()
    var sb: Regex = friends
    var sa: Regex = always
    var t: String = ""
    var bc: Regex? = null
    var r: String? = null

    fun directions(d: Array<String>): Mb {
        this.d = d
        return this
    }

    fun stopBefore(sb: Regex): Mb {
        this.sb = sb
        return this
    }

    fun stopAfter(sa: Regex): Mb {
        this.sa = sa
        return this
    }

    fun tag(t: String): Mb {
        this.t = t
        return this
    }

    fun boardCondition(bc: Regex): Mb {
        this.bc = bc
        return this
    }

    fun replacement(r: String): Mb {
        this.r = r
        return this
    }

    fun build(): Moveset {
        return Moveset(d, sb, sa, t, bc, r)
    }
}
