val occupied: Regex = ".\\w".toRegex()
val always: Regex = "..".toRegex()

val enemies: Regex = "[A-Z][a-z]|[a-z][A-Z]".toRegex()
val friends: Regex = "[A-Z][A-Z]|[a-z][a-z]".toRegex()

val notEnemies: Regex = "[A-Z][A-Z ]|[a-z][a-z ]".toRegex()
