package irawan.electroshock.weenak.utils

class SetterGetter {

    var sgFoodName : String = ""
        get() = field
        set(value) {
            field = value
        }

    var sgFoodImage : List<String>? = null
        get() = field
        set(value) {
            field = value
        }

    var sgFoodDescription : String = ""
        get() = field
        set(value) {
            field = value
        }

    var sgFoodPreparationSteps : List<String>? = null
        get() = field
        set(value) {
            field = value
        }

    var sgFoodVideos : String? = ""
        get() = field
        set(value) {
            field = value
        }

    var sgFoodTotalTime : String? = ""
        get() = field
        set(value) {
            field = value
        }

    var sgFoodNumberOfServing : String? = ""
        get() = field
        set(value) {
            field = value
        }

    var sgFoodRatings : String? = ""
        get() = field
        set(value) {
            field = value
        }

    var sgFoodIngredients : List<SgFoodIngredient>? = null
        get() = field
        set(value) {
            field = value
        }
}

class SgFoodIngredient {

    var sgFoodIngredient : String? = ""
        get() = field
        set(value) {
            field = value
        }

}
