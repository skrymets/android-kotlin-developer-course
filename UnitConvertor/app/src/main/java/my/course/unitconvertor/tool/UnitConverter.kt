package my.course.unitconvertor.tool


/**
 * Metric (SI) Prefixes
 * https://www.nist.gov/pml/owm/metric-si-prefixes
 */
open class Prefix(val prefix: String, val factor: Double) : Comparable<Prefix> {
    override fun compareTo(other: Prefix): Int = this.factor.compareTo(other.factor)
}

object peta : Prefix("peta", 1.0e15)
object tera : Prefix("tera", 1.0e12)
object giga : Prefix("giga", 1.0e9)
object mega : Prefix("mega", 1.0e6)
object kilo : Prefix("kilo", 1.0e3)
object hecto : Prefix("hecto", 1.0e2)
object deka : Prefix("deka", 1.0e1)
object base : Prefix("", 1.0e0)
object deci : Prefix("deci", 1.0e-1)
object centi : Prefix("centi", 1.0e-2)
object milli : Prefix("milli", 1.0e-3)
object micro : Prefix("micro", 1.0e-6)
object nano : Prefix("nano", 1.0e-9)
object pico : Prefix("pico", 1.0e-12)


fun convert(multiplier: Double, fromFactor: Prefix, toFactor: Prefix): Double {
    return (multiplier * fromFactor.factor) / toFactor.factor
}

class MeasureValue(val value: Double, val prefix: Prefix) : Comparable<MeasureValue> {
    inline operator fun plus(other: MeasureValue): MeasureValue {
        return MeasureValue(value + convert(other.value, other.prefix, prefix), prefix)
    }

    inline operator fun minus(other: MeasureValue): MeasureValue {
        return MeasureValue(value - convert(other.value, other.prefix, prefix), prefix)
    }

    inline operator fun unaryMinus(): MeasureValue = MeasureValue(-value, prefix)

    inline operator fun div(other: MeasureValue): Double {
        return value / convert(other.value, other.prefix, prefix)
    }

    inline operator fun div(other: Double): MeasureValue = MeasureValue(value / other, prefix)
    inline operator fun div(other: Int): MeasureValue = MeasureValue(value / other, prefix)

    inline operator fun times(other: Double): MeasureValue = MeasureValue(value * other, prefix)
    inline operator fun times(other: Int): MeasureValue = MeasureValue(value * other, prefix)

    override fun compareTo(other: MeasureValue): Int = value.compareTo(other.value)
    fun isUndefined(): Boolean = value.isNaN()
    override fun toString(): String = if (isUndefined()) "undefined" else value.toString()
}

inline val Double.petametre: MeasureValue get() = MeasureValue(this, peta)
inline val Double.terametre: MeasureValue get() = MeasureValue(this, tera)
inline val Double.gigametre: MeasureValue get() = MeasureValue(this, giga)
inline val Double.megametre: MeasureValue get() = MeasureValue(this, mega)
inline val Double.kilometre: MeasureValue get() = MeasureValue(this, kilo)
inline val Double.hectometre: MeasureValue get() = MeasureValue(this, hecto)
inline val Double.dekametre: MeasureValue get() = MeasureValue(this, deka)
inline val Double.metre: MeasureValue get() = MeasureValue(this, base)
inline val Double.decimetre: MeasureValue get() = MeasureValue(this, deci)
inline val Double.centimetre: MeasureValue get() = MeasureValue(this, centi)
inline val Double.millimetre: MeasureValue get() = MeasureValue(this, milli)
inline val Double.micrometre: MeasureValue get() = MeasureValue(this, micro)
inline val Double.nanometre: MeasureValue get() = MeasureValue(this, nano)
inline val Double.picometre: MeasureValue get() = MeasureValue(this, pico)

operator fun Double.times(other: MeasureValue): MeasureValue = other * this


fun main() {
    println(100.0.metre / 3.0.metre)
    println(1.0.metre / 1.0.centimetre)
    println(5.0.centimetre / 1.0.metre)
    println(10.0 * 5.0.centimetre)
//    println(convert(1.0e0, base, centi))
//    println(convert(1.0e0, centi, base))
}