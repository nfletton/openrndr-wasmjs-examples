# OPENRNDR Kotlin/Wasm & Kotlin/JS Examples

This is a set of examples for the [OPENRNDR](https://openrndr.org/) library using the Kotlin/Wasm & Kotlin/JS targets.
The examples cover the basics of OPENRNDR by replicating examples from the 
[OPENRNDR Guide](https://guide.openrndr.org) and demo programs from 
[OPENRNDR ORX](https://github.com/openrndr/orx).

Wasm & JS targets are at an early stage of development. Initial results from these examples
show that a Wasm production build results in a 50% increase in data transfer over 
the JS target.
Unscientifically comparing individual demos across targets, GPU load looks very similar
and CPU load looks higher on Wasm. The increase in CPU load may be a result of the
bridging overhead necessary for the Wasm runtime to access WebGL via JavaScript.

## Live demo

A live example of this project can be found at https://wasm.zygal.com/.

## Developing

### Running locally

To get started run

```bash
# Wasm Examples 
./gradlew wasmJsBrowserDevelopmentRun -t
```

```bash
# JavaScript Examples
./gradlew jsBrowserDevelopmentRun -t
```

Each of the above commands will start a local development server with hot-reloading
for the respective target.

NOTE: The web interface provides buttons that support switching between the Wasm and 
JavaScript outputs when installed in a suitable production environment. These do not 
function in development.

Any changes saved under `/src/webMain/kotlin`, `/src/wasmJsMain/kotlin` 
and `/src/jsMain/kotlin` source sets, will be reflected in the browser.

Most code should be located under the `webMain` source set so that it is shared 
between the Wasm and JavaScript targets.

## Exporting

During development the produced JavaScript program occupies a few megabytes.
Once the project is ready to be shared, one can export a minimized executable by running

```bash
# Wasm
./gradlew wasmJsBrowserDistribution
```

```bash
# JavaScript
./gradlew jsBrowserDistribution
```

These commands will place the resulting production files into the
`./build/dist/<target name>/productionExecutable/` folders.

## TODOs / Potentials
- [ ] Add more demos, of course.
- [ ] Switch back to GitHub pages and use Abe's workflow to get both targets built and deployed automatically.
- [ ] Canvas looks to have a resizing issue on page load. Not visible when the navigation bar is at its default width, but when it has been widened, there's a distinct reload of the canvas going on.
- [ ] Add additional status text indicating any known issues with the demo compared to the JVM version.
- [ ] Load first sketch if no `sketch` parameter in URL
- [ ] ~~Link to the original JVM demo code as well as the web version currently linked to~~. Not necessary as the code accessible via the documentation link.
