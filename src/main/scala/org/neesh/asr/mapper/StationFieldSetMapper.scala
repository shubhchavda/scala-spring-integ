package org.neesh.asr.mapper

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import org.neesh.asr.beans.{RecordType, StationHeader}

/**
 * Created by IntelliJ IDEA.
 * User: Shubh Chavda
 * Date: 3/28/11
 * Time: 9:35 PM
 * To change this template use File | Settings | File Templates.
 */

class StationFieldSetMapper extends FieldSetMapper[StationHeader] {

    def mapFieldSet(fs : FieldSet) : StationHeader = {

      val recordType = new RecordType(fs.readString("id"), fs.readString("qualifier"))

        return new StationHeader(recordType, fs.readString(StationFields.NUMBER.toString),
                                        fs.readString(StationFields.SALEDATE.toString),
                                        fs.readString(StationFields.CODE.toString),
                                        fs.readString(StationFields.CURRENCY.toString))
    }
}

object StationFields extends Enumeration{

    val NUMBER = Value("stationNumber")
    val CODE = Value("code")
    val CURRENCY = Value("currency")
    val SALEDATE = Value("saleDate")

}

