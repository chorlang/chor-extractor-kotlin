package epp

class MergingException : Exception {
    constructor(m: String) : super(m) {}

    constructor(message: String, cause: Throwable) : super(message, cause) {}
}
