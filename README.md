<p align="center">
<img src="/art/header.png" />
</p>

# GithubUser App (work in progress)

<a href="https://android-arsenal.com/api?level=23#l23"><img alt="API" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat"/></a>

A sample project demonstrating the use of the latest design patterns, libraries, architecture and more.

It fetches data from **Github** [search](https://docs.github.com/en/rest/reference/search) and [user details](https://docs.github.com/en/rest/reference/users) APIs and show it to the user in the list and detailed screens. A searched user can be starred from the list or the details screen and the data will be saved in the local database. A user profile can be shared or open in a browser from the details screen.


## Tech stack and libraries

### Kotlin
- [Coroutine](https://kotlinlang.org/docs/coroutines-overview.html) - Doing work asynchronously
- [Flow](https://kotlinlang.org/docs/flow.html) - Getting results from asynchronous workers as soon as they're available

### Android Jetpack
- [HILT](https://dagger.dev/hilt/) - Dependency injection
- [Navigation](https://developer.android.com/guide/navigation) - Moving between activities and fragments
- [Paging](https://developer.android.com/jetpack/androidx/releases/paging) - Load data in small chunks as user scroll list
- [Room](https://developer.android.com/training/data-storage/room) - Saving user data on device locally 

### Networking
- [Retrofit2](http://square.github.io/retrofit/) - REST Client for Android and Java
- [Okhttp3 Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) - An OkHttp interceptor which logs HTTP request and response data (used with Retrofit)
- [Moshi](https://github.com/square/moshi) - To convert Java objects to JSON and back (used with Retrofit)

### Testing
- [Espresso](https://developer.android.com/training/testing/espresso) - Easy, concise and powerful UI testing
- [MockK](https://mockk.io/ANDROID.html) - Mocking library for Kotlin
- [Truth](https://truth.dev/) - Fluent assertions for Java and Android
  
### Tools
- [Glide](https://bumptech.github.io/glide/) -  Fast and efficient image loading
- [Timber](https://github.com/JakeWharton/timber) - Logger with a small, extensible API providing utility on top of Android's normal Log class

## Upcoming features

Updates will include incorporating additional Jetpack components and updating existing ones as the component libraries evolve.

Interested in seeing a particular feature? Please [open a new issue](https://github.com/shahrukhamd/GithubUser/issues/new/choose).


## License

    MIT License (https://opensource.org/licenses/MIT)

    Copyright (c) 2021 Shahrukh Ahmed Siddiqui

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.


## Support

This project is authored and maintained by [shahrukhamd](https://shahrukhamd.com/). 
You can show your support by buying me a coffee :)

<a href="https://www.buymeacoffee.com/shahrukhamd"><img alt="buy me coffee" src="/art/buymecoffee.png"/></a>