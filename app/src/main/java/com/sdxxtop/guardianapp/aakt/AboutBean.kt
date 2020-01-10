package com.sdxxtop.guardianapp.aakt

/**
 * @author :  lwb
 * Date: 2020/1/10
 * Desc:
 */

data class AboutBean(
    val echarts: List<Echart>,
    val lists: Lists
)

data class Echart(
    val month: Int,
    val score: Int
)

data class Lists(
    val matter_score: Int,
    val other_score: Int,
    val patrol_score: Int,
    val score: Int,
    val usually_score: Int
)