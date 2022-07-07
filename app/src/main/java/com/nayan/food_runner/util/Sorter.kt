package com.nayan.food_runner.util

import com.nayan.food_runner.models.Restaurants

class Sorter {
    companion object {
        var costComparator = Comparator<Restaurants> { res1, res2 ->
            val costOne = res1.costForOne
            val costTwo = res2.costForOne
            if (costOne.compareTo(costTwo) == 0) {
                ratingComparator.compare(res1, res2)
            } else {
                costOne.compareTo(costTwo)
            }
        }

        var ratingComparator = Comparator<Restaurants> { res1, res2 ->
            val ratingOne = res1.resRatings
            val ratingTwo = res2.resRatings
            if (ratingOne.compareTo(ratingTwo) == 0) {
                val costOne = res1.costForOne
                val costTwo = res2.costForOne
                costOne.compareTo(costTwo)
            } else {
                ratingOne.compareTo(ratingTwo)
            }
        }
    }

}