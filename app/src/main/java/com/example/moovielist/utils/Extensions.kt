package com.example.moovielist.utils


    fun String.limitedDescription(limit: Int): String {
        if (this.length > limit) {
            val firstCharacter = 0
            return "${this.substring(firstCharacter, limit)}..."
        }
        return this
    }

