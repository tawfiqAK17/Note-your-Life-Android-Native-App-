package taw.ak.noteyourlife

sealed class Screens(var route:String) {
    object MainScreen : Screens("main_screen")
    object SavedNotes : Screens("saived_notes")
    object SavedToDos : Screens("saived_todos")
    object AddNote : Screens("add_note")
    object AddToDo : Screens("add_to_do")
    object EditeNote : Screens("edite_note")
}