package student.testing.system.common


class AccountSession {
    var token: String? = null
    var userId: Int? = null
    var email: String? = null
    var username: String? = null


    companion object {
        var instance: AccountSession = AccountSession()
    }
}