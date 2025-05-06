package com.ckenken.composeexperiment

import CollapsingToolbarWithExpandableSections
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ckenken.composeexperiment.ui.theme.ComposeExperimentTheme
import kotlin.math.max
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeExperimentTheme {
                CollapsingToolbarWithExpandableSections()
            }

        }
        val kkman = solution(intArrayOf(0, 9, 0, 2, 6, 8, 0, 8, 3, 0))
        Log.d("ckenken", "answer = $kkman")
    }

    fun solution4(A: IntArray): Int {
        if (A.size == 2) {
            return 1
        }

        val list = A.toList()

        // key = sum, pair = listOf( (index1 to index2), (index1 to index 2).. )
        val map = HashMap<Int, List<Pair<Int, Int>>>()

        for (i in 0..list.size - 2) {
            val sum = list[i] + list[i+1]
            val currentMapItem = map[sum]?.toMutableList() ?: mutableListOf()
            map[sum] = currentMapItem.apply {
                add(i to i + 1)
            }
        }

        var max = 1
        map.forEach { (key, list) ->
            var count = 0
            if (list.size > 1) {
                var currentPair = Pair(-1, -1)

                list.forEach{ item ->
                    if (item.first != currentPair.second) {
                        count++
                        currentPair = item
                    }
                }
            }
            if (max < count) {
                max = count
            }
        }
        return max
    }

//    fun solution2(N: Int, A: IntArray, B: IntArray): Boolean {
//        val pairList = mutableListOf<Pair<Int, Int>>()
//
//        for (i in 0..N-1) {
//            val newPair = (min(A[i], B[i]) to max(A[i], B[i]))
//            pairList.add(newPair)
//        }
//        val sortedList = pairList.sortedBy { it.first }
//
//    }

//    fun solution3(T: IntArray): Int {
//        val list = mutableListOf<Pair<Int, Int>>()
//        T.forEachIndexed { index, item ->
//            if (index != item) {
//                list.add(index to item)
//                list.add(item to index)
//            }
//        }
//    }

    var max: Int = 0

    fun solution(T: IntArray): Int {
        val list = mutableListOf<Pair<Int, Int>>()
        T.forEachIndexed { index, item ->
            if (index != item) {
                list.add(index to item)
                list.add(item to index)
            }
        }

        gogogo(list, listOf(),0, 0, false)

        return max
    }
//   [(1, 9), (9, 1), (2, 0), (0, 2), (3, 2), (2, 3), (4, 6), (6, 4), (5, 8), (8, 5), (6, 0), (0, 6), (7, 8), (8, 7), (8, 3), (3, 8), (9, 0), (0, 9)]

    fun gogogo(
        list: List<Pair<Int, Int>>,
        passed: List<Pair<Int, Int>>,
        currentPosition: Int,
        currentStep: Int,
        currentOdd: Boolean,
    ) {
        if (currentStep > max) {
            max = currentStep
        }
        val filteredList = list.filter { it.first == currentPosition }
        filteredList.forEach {
            when {
                it.second % 2 == 0 -> {
                    if (passed.contains(currentPosition to it.second).not()) {
                        val newPassed = passed.toMutableList().apply {
                            add(currentStep to it.second)
                            add(it.second to currentPosition)
                        }
                        gogogo(list, newPassed, it.second, currentStep + 1, currentOdd)
                    }
                }
                it.second % 2 == 1 -> {
                    if (currentOdd) {

                    } else {
                        val newPassed = passed.toMutableList().apply {
                            add(currentStep to it.second)
                            add(it.second to currentPosition)
                        }
                        gogogo(list,newPassed, it.second, currentStep + 1, true)
                    }
                }
            }
        }
    }
}