package com.oguzhanturkmen.myplantapp.data.models

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var userId: String? = null,
    var userEmail: String? = null,
    var ppUrl: String? = null
) {
}