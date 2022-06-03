package es.ideas.holaevento

import com.google.firebase.firestore.Exclude

data class Provincia(
    var name: String? =""
){
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "name" to name
        )
    }
}
