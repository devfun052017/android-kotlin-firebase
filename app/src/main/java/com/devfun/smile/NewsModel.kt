package com.devfun.smile

import android.support.annotation.Keep
import java.io.Serializable

/**
 * *******************************************
 * * Created by Simon on 5/9/17.            **
 * * Copyright (c) 2015 by DevFun           **
 * * All rights reserved                    **
 * *******************************************
 */

@Keep
class NewsModel : Serializable{

    var title: String? = ""
    var short_description: String? = ""
    var description: String? = ""
    var image: String? = ""
    var posted: String? = ""
    var type: String? = ""

}
