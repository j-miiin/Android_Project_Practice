package com.acaroom.a08_15_practice

import java.io.Serializable

class Post(
    var owner: String? = null,
    var content: String? = null,
    var image: String? = null
): Serializable