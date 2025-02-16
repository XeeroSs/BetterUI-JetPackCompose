package fr.xeross.betterui.extensions

import java.util.regex.Pattern

object StringExtension {

    private const val EMAIL_FORMAT =
        "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"


    fun String.isFormatEmail(): Boolean {
        val pattern = Pattern.compile(EMAIL_FORMAT, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(this)
        return matcher.matches()
    }

    fun String.isValidPassword(
        minLength: Int = 8,
        requireUppercase: Boolean = true,
        requireLowercase: Boolean = true,
        requireDigit: Boolean = true,
        requireSpecialChar: Boolean = true
    ): Boolean {
        val regexBuilder = StringBuilder("^")

        if (requireUppercase) regexBuilder.append("(?=.*[A-Z])")
        if (requireLowercase) regexBuilder.append("(?=.*[a-z])")
        if (requireDigit) regexBuilder.append("(?=.*\\d)")
        if (requireSpecialChar) regexBuilder.append("(?=.*[!@#&()\\[\\]{}:;'_,?/*~\$^+=<>])")

        regexBuilder.append(".{$minLength,}$")

        val pattern = Pattern.compile(regexBuilder.toString())
        return pattern.matcher(this).matches()
    }


}