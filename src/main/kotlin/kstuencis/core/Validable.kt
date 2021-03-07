package kstuencis.core

interface Validable {
    fun isInvalid(): Boolean
    fun isValid(): Boolean
}