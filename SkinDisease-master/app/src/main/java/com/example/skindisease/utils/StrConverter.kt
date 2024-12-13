package com.example.skindisease.utils

import android.net.Uri

fun String.toUri(): Uri {
    return Uri.parse(this)
}