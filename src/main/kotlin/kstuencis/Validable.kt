package kstuencis

interface Validable {
    fun isInvalid(): Boolean
    fun isValid(): Boolean
}