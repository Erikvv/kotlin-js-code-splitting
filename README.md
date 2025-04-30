This repository contains a minimal example of how to do code splitting in 
Kotlin/JS.

To build: `./gradlew build`

To run in the Node.js repl:

```js
node

const mainModule = await import(
    "./build/kotlin-webpack/js/productionExecutable/codesplitting.js"
)
mainModule.main()
```

What is happening?
---

`Main.kt` asynchronously imports `fun chair` from `Chair.kt`. Code splitting
is taking placing: there are two JavaScript files.

`Main.kt` directly imports `fun kofi` from `Kofi.kt`. No code splitting 
takes place, the code ends up in the same JavaScript file. 

Configuration
---

Essential elements needed to make this work:

```kotlin
// build.gradle.kts
kotlin {
    compilerOptions {
        // Compile each .kt file to a separate .mjs file
        compilerOptions.freeCompilerArgs.add("-Xir-per-file")
    }
}
```

```kotlin
// Chair.kt
@JsExport
fun chair() {
    /* ... */
}
```

```kotlin
// Main.kt
importAsync<ChairModule>("./Chair.export.mjs")
    .then { it.chair() }
```

```js
// webpack.config.d/codesplitting.js
config.entry = {
    main: require('path').resolve(
        __dirname, 
        "kotlin/codesplitting/com/zenmo/Main.export.mjs"
    )
}
```
