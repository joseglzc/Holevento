package es.ideas.holaevento

import com.google.firebase.firestore.Exclude

data class Provincia(
    var id: Int=0,
    var name: String? =""
){
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "id" to id,
            "name" to name
        )
    }
}
