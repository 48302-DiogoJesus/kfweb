package _others


/*
class Car private constructor (val name: String, val gasoline: Boolean, val owner: Person) {
    companion object {
        class Builder {
            var name by Delegates.notNull<String>()
            var gasoline by Delegates.notNull<Boolean>()
            var owner by Delegates.notNull<Person>()
        }

        fun build(builderBlock: Builder.() -> Unit): Car {
            val builder = Builder()
            builder.builderBlock()
            return Car(builder.name, builder.gasoline, builder.owner)
        }
    }
}


class Person private constructor (val name: String, val age: Int, val school: String) {
    companion object {
        class Builder {
            var name by Delegates.notNull<String>()
            var age by Delegates.notNull<Int>()
            var school by Delegates.notNull<String>()
        }

        fun build(builderBlock: Builder.() -> Unit): Person {
            val builder = Builder()
            builder.builderBlock()
            return Person(builder.name, builder.age, builder.school)
        }
    }
}
*/

//
//class Car private constructor (builder: Car.Builder) {
//    val gasoline: Boolean = true
//    val name: String
//
//    class Builder {
//        val gasoline: Boolean = true
//        val name: String
//
//        fun setName(name: String) {
//            this.name = name
//        }
//
//        fun builder() = Car(this)
//    }
//}