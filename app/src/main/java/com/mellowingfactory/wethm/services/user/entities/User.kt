package com.mellowingfactory.wethm.services.user.entities

data class User(
    var id: String? = null,
    var email: String? = null,
    var name: String? = null,
    var familyName: String? = null,
    var age: Int? = null,
    var height: Int? = null,
    var weight: Int? = null,
    var marketing: Boolean? = null,
    var gender: String? = null,
    var created: String? = null,
    var updated: String? = null,
    var offset: Int? = null,
    var membership: String? = null,
    var fakeLocation: String? = null,
    var role: Int? = null
)

data class CreateUserRequest (
    var item: User
)

data class GetUserResponse (
    var data: User
)

data class UpdateUserRequest (
    var id: String,
    var item: User
)