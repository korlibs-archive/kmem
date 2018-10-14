# Kmem

Memory utilities for Kotlin

[![Build Status](https://travis-ci.org/korlibs/kmem.svg?branch=master)](https://travis-ci.org/korlibs/kmem)
[![Maven Version](https://img.shields.io/github/tag/korlibs/kmem.svg?style=flat&label=maven)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22kmem%22)
[![Gitter](https://img.shields.io/gitter/room/korlibs/korlibs.svg)](https://gitter.im/korlibs/Lobby)

Use with gradle (uploaded to bintray and synchronized to jcenter):

```
def kmemVersion = "0.9.0"

repositories {
    maven { url "https://dl.bintray.com/soywiz/soywiz" }
}

compile "com.soywiz:kmem-jvm:$kmemVersion"      // JVM/Android
compile "com.soywiz:kmem-js:$kmemVersion"       // JavaScript
compile "com.soywiz:kmem-iosarm32:$kmemVersion" // Older iOS devices
compile "com.soywiz:kmem-iosarm64:$kmemVersion" // New iOS devices
compile "com.soywiz:kmem-iosx64:$kmemVersion"   // Simulator
compile "com.soywiz:kmem-linuxx64:$kmemVersion" // Linux x64
compile "com.soywiz:kmem-macosx64:$kmemVersion" // MacOS
compile "com.soywiz:kmem-mingwx64:$kmemVersion" // Windows x64
compile "com.soywiz:kmem-common:$kmemVersion"   // Common (just expect 2 decls in kmem)
```
