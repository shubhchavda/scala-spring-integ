package org.neesh.asr

import org.springframework.batch.item.file.transform.LineAggregator

/**
 * Created by IntelliJ IDEA.
 * User: Shubh Chavda
 * Date: 3/29/11
 * Time: 7:21 AM
 * To change this template use File | Settings | File Templates.
 */

class DummyLineAggregator[T] extends LineAggregator[T]{

    def aggregate(item : T) : String = {
        println("In aggregate")
        println(item.toString)
        "Dummy"
    }
}