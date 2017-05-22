package com.devfun.smile

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

/**
 * *******************************************
 * * Created by Simon on 5/9/17.            **
 * * Copyright (c) 2015 by AppsCyclone      **
 * * All rights reserved                    **
 * * http://appscyclone.com/                **
 * *******************************************
 */

class NewsModel : Serializable{

    var title: String? = null
    var short_description: String? = null
    var description: String? = null
    var image: String? = null
    var posted: String? = null
    var type: String? = null
}
