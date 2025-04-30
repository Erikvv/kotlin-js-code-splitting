package com.zenmo

import js.import.importAsync

@JsExport
fun main() {
    console.log("main!")
    runChair()
    kofi()
}

external interface ChairModule {
    fun chair()
}

fun runChair() {
    // Use "dynamic" instead of "ChairModule" if you're lazy
    importAsync<ChairModule>("./Chair.export.mjs")
        .then { it.chair() }
}
