# OPENRNDR Kotlin/Wasm Examples

This is a set of examples for the [OPENRNDR](https://openrndr.org/) library using the Kotlin/Wasm target.
The examples cover the basics of OPENRNDR by replicating many of the examples from the 
[OPENRNDR Guide](https:/guide.openrndr.org) and extend to the demo programs from 
[OPENRNDR ORX](https://github.com/openrndr/orx)

[//]: # (## Live demo)

[//]: # ()
[//]: # (TBD)

## Developing

To get started run

```bash
./gradlew wasmJsBrowserDevelopmentRun -t
```

This will start a local development server with hot-reloading.

Any changes saved under `/src/wasmJsMain/kotlin` will be reflected
in the browser.


## Exporting

During development the produced JavaScript program occupies a few megabytes.
Once the project is ready to be shared, one can export a minimized executable by running

```bash
#./gradlew wasmJsBrowserProductionWebpack
./gradlew wasmJsBrowserDistribution
```

This will place the resulting files into the
`build/dist/wasmJs/productionExecutable/` folder.

[//]: # (## JavaScript communication)

[//]: # ()
[//]: # (In some cases it can be useful to include additional JavaScript in the HTML template,)

[//]: # (for instance to have the Kotlin program communicate with a remote web server,)

[//]: # (to synthesize sound using the web audio API or to interact with an HTML form or GUI.)

[//]: # ()
[//]: # (Read about Kotlin <-> JavaScript communication at)

[//]: # (https://kotlinlang.org/docs/js-interop.html#external-modifier)

[//]: # (### A. Kotlin talking to JavaScript)

[//]: # ()
[//]: # (This is an example of having Kotlin update a JavaScript variable, and call a JavaScript function.)

[//]: # ()
[//]: # (#### TemplateProgram.kt)

[//]: # ()
[//]: # (```kotlin)

[//]: # (// Reference to a JavaScript variable declared in index.html)

[//]: # (external var globalCounter: Int)

[//]: # ()
[//]: # (// Reference to a JavaScript function declared in index.html)

[//]: # (external fun greet&#40;name: String&#41;)

[//]: # ()
[//]: # (fun main&#40;&#41; = application {)

[//]: # (    program {)

[//]: # (        extend {)

[//]: # (            // Update a JavaScript variable)

[//]: # (            globalCounter = frameCount)

[//]: # ()
[//]: # (            // ...)

[//]: # (        })

[//]: # (        mouse.buttonDown.listen {)

[//]: # (            // Call a JavaScript function)

[//]: # (            greet&#40;"TemplateProgram.kt"&#41;)

[//]: # (        })

[//]: # (    })

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (#### index.html)

[//]: # (```html)

[//]: # (<script language="JavaScript">)

[//]: # (    let globalCounter = 0;)

[//]: # (    function greet&#40;name&#41; {)

[//]: # (        console.log&#40;"JS says hello, " + name + "! " + globalCounter&#41;;)

[//]: # (    })

[//]: # (</script>)

[//]: # (```)

[//]: # ()
[//]: # (### B. JavaScript calling a Kotlin function)

[//]: # ()
[//]: # (This example shows how to pass a Kotlin callback function to)

[//]: # (JavaScript. The function is stored, then called every)

[//]: # (5 seconds. This approach can be used if we need our visuals to react to JavaScript events.)

[//]: # ()
[//]: # (#### TemplateProgram.kt)

[//]: # ()
[//]: # (```kotlin)

[//]: # (// Reference to a JavaScript function declared in index.html)

[//]: # (// The function takes one argument: a Kotlin function.)

[//]: # (external fun setCallback&#40;f: &#40;&#41; -> Unit&#41;)

[//]: # ()
[//]: # (fun main&#40;&#41; = application {)

[//]: # (    program {)

[//]: # (        // Pass a Kotlin function to JavaScript)

[//]: # (        setCallback {)

[//]: # (            // In this simple example we just log something)

[//]: # (            console.log&#40;"Interval Kotlin"&#41;)

[//]: # (        })

[//]: # ()
[//]: # (        extend {)

[//]: # (            // ...)

[//]: # (        })

[//]: # (    })

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (#### index.html)

[//]: # ()
[//]: # (```html)

[//]: # (<script language="JavaScript">)

[//]: # (    let callback = function&#40;&#41; {})

[//]: # (    )
[//]: # (    // Store the received function for later use )

[//]: # (    function setCallback&#40;cb&#41; {)

[//]: # (        callback = cb;)

[//]: # (    })

[//]: # (    )
[//]: # (    // Execute `callback` every 5 seconds)

[//]: # (    setInterval&#40;function&#40;&#41; {)

[//]: # (        console.log&#40;"Interval JS"&#41;;)

[//]: # (        callback&#40;&#41;;)

[//]: # (    }, 5000&#41;;)

[//]: # (</script>)

[//]: # (```)